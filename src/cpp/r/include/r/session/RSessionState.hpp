/*
 * RSessionState.hpp
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

#ifndef R_R_SESSION_STATE_HPP
#define R_R_SESSION_STATE_HPP

#include <string>

#include <boost/function.hpp>

#include <shared_core/Error.hpp>
#include <core/Version.hpp>

namespace rstudio {
namespace core {
   class FilePath;
}
}

namespace rstudio {
namespace r {
namespace session {
namespace state {

struct SessionStateInfo
{
   core::Version suspendedRVersion;
   core::Version activeRVersion;
};

struct SessionStateCallbacks
{
   boost::function<void(const std::string&)> consoleWriteInput;
};

void initialize(SessionStateCallbacks callbacks);

bool save(const core::FilePath& statePath,
          const std::string& afterRestartCommand,
          const std::string& builtPackagePath,
          bool serverMode,
          bool excludePackages,
          bool disableSaveCompression,
          bool saveGlobalEnvironment,
          const std::string& ephemeralEnvVars);

bool saveMinimal(const core::FilePath& statePath,
                 const std::string& afterRestartCommand,
                 bool saveGlobalEnvironment);
   

bool rProfileOnRestore(const core::FilePath& statePath);

bool packratModeEnabled(const core::FilePath& statePath);

bool restore(const core::FilePath& statePath, 
             bool serverMode,
             boost::function<core::Error()>* pDeferredRestoreAction,
             std::string* pErrorMessages);
   
bool destroy(const core::FilePath& statePath);

SessionStateInfo getSessionStateInfo();
     
} // namespace state
} // namespace session
} // namespace r
} // namespace rstudio

#endif // R_R_SESSION_STATE_HPP

