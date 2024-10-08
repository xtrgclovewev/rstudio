/*
 * RSuspend.cpp
 *
 * Copyright (C) 2022 by Posit Software, PBC
 *
 * Unless you have received this program directly from Posit Software pursuant
 * to the terms of a commercial license agreement with Posit Software, then
 * this program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */

#include <Rembedded.h>

#include <shared_core/FilePath.hpp>

#include <core/FileSerializer.hpp>
#include <core/system/Environment.hpp>

#include <r/RExec.hpp>
#include <r/session/RClientState.hpp>
#include <r/session/RGraphics.hpp>
#include <r/session/RSession.hpp>
#include <r/session/RSessionState.hpp>
#include <r/session/RSuspend.hpp>

#include "REmbedded.hpp"
#include "RRestartContext.hpp"
#include "RStdCallbacks.hpp"

using namespace rstudio::core;

namespace rstudio {
namespace r {
namespace session {

namespace {

// session state path
FilePath s_suspendedSessionPath;

// client-state paths
FilePath s_clientStatePath;
FilePath s_projectClientStatePath;
   
// are in the middle of servicing a suspend request?
bool s_suspended = false;

bool saveSessionState(const RSuspendOptions& options,
                      const FilePath& suspendedSessionPath,
                      bool disableSaveCompression)
{
   // notify client of serialization status
   SerializationCallbackScope cb(kSerializationActionSuspendSession);
   
   // suppress interrupts which occur during saving
   r::exec::IgnoreInterruptsScope ignoreInterrupts;
   
   // check for save workspace override
   std::string saveWorkspaceOverride =
         core::system::getenv("RSTUDIO_SUSPEND_SAVE_WORKSPACE");
   
   // save 
   if (options.saveMinimal)
   {
      bool saveWorkspace = string_utils::isTruthy(
               saveWorkspaceOverride,
               options.saveWorkspace);
      
      // save minimal
      return r::session::state::saveMinimal(
               suspendedSessionPath,
               options.afterRestartCommand,
               saveWorkspace);

   }
   else
   {
      // NOTE: Just to preserve prior behavior, we always choose
      // to save the workspace here, even if options.saveWorkspace
      // is set to false. We still allow the environment override.
      bool saveWorkspace = string_utils::isTruthy(
               saveWorkspaceOverride,
               true);
      
      return r::session::state::save(
               suspendedSessionPath,
               options.afterRestartCommand,
               options.builtPackagePath,
               utils::isServerMode(),
               options.excludePackages,
               disableSaveCompression,
               saveWorkspace,
               options.ephemeralEnvVars);
   }
}
   
} // anonymous namespace

void setSuspendPaths(const FilePath& suspendedSessionPath,
                     const FilePath& clientStatePath,
                     const FilePath& projectClientStatePath)
{
   s_suspendedSessionPath = suspendedSessionPath;
   s_clientStatePath = clientStatePath;
   s_projectClientStatePath = projectClientStatePath;
   
}

FilePath suspendedSessionPath()
{
   return s_suspendedSessionPath;
}
   
bool suspend(const RSuspendOptions& options,
             const FilePath& suspendedSessionPath,
             bool disableSaveCompression,
             bool force)
{
   // validate that force == true if disableSaveCompression is specified
   // this is because save compression is disabled and the previous options
   // are not restored, so it is only suitable to use this when we know
   // the process is going to go away completely
   if (disableSaveCompression)
      BOOST_ASSERT(force == true);

   // commit all client state
   saveClientState(ClientStateCommitAll);

   // if we are saving minimal then clear the graphics device
   if (options.saveMinimal)
   {
      r::session::graphics::display().clear();
   }

   // save the session state. errors are handled internally and reported
   // directly to the end user and written to the server log.
   bool suspend = saveSessionState(options,
                                   suspendedSessionPath,
                                   disableSaveCompression);
      
   // if we failed to save the data and are being forced then warn user
   if (!suspend && force)
   {
      reportAndLogWarning("Forcing suspend of process in spite of all session "
                          "data not being fully saved.");
      suspend = true;
   }
   
   // only continue with exiting the process if we actually succeed in saving
   if (suspend)
   {      
      // set suspended flag so cleanup code can act accordingly
      s_suspended = true;
      
      // call suspend hook
      rCallbacks().suspended(options);
   
      // clean up but don't save workspace or runLast because we have
      // been suspended
      RCleanUp(SA_NOSAVE, options.status, FALSE);
      
      // keep compiler happy (this line will never execute)
      return true;
   }
   else
   {
      return false;
   }
}

bool suspend(bool force, int status, const std::string& ephemeralEnvVars)
{
   return suspend(RSuspendOptions(status, ephemeralEnvVars),
                  s_suspendedSessionPath,
                  false,
                  force);
}

void suspendForRestart(const RSuspendOptions& options)
{
   suspend(options,
           RestartContext::createSessionStatePath(utils::scopedScratchPath(),
                                                  utils::sessionPort()),
           true,  // disable save compression
           true);  // force suspend
}

SerializationCallbackScope::SerializationCallbackScope(int action,
                           const FilePath& targetPath)
{
   rCallbacks().serialization(action, targetPath);
}

SerializationCallbackScope::~SerializationCallbackScope()
{
   try
   {
      rCallbacks().serialization(kSerializationActionCompleted, FilePath());
   }
   catch(...)
   {
   }
}

bool suspended()
{
   return s_suspended;
}

void saveClientState(ClientStateCommitType commitType)
{
   using namespace r::session;

   // save client state (note we don't explicitly restore this
   // in restoreWorkingState, rather it is restored during
   // initialize() so that the client always has access to it when
   // for client_init)
   r::session::clientState().commit(commitType,
                                    s_clientStatePath,
                                    s_projectClientStatePath);
}

namespace utils
{

core::FilePath clientStatePath()
{
   return s_clientStatePath;
}

core::FilePath projectClientStatePath()
{
   return s_projectClientStatePath;
}

core::FilePath suspendedSessionPath()
{
   return s_suspendedSessionPath;
}

} // namespace utils

} // namespace session
} // namespace r
} // namespace rstudio
