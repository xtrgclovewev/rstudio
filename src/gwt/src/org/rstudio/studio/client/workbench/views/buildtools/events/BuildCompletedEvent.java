/*
 * BuildCompletedEvent.java
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
package org.rstudio.studio.client.workbench.views.buildtools.events;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class BuildCompletedEvent extends GwtEvent<BuildCompletedEvent.Handler>
{
   public static class Data extends JavaScriptObject
   {
      protected Data()
      {

      }

      public native final boolean getRestartR() /*-{
         return this.restart_r;
      }-*/;

      public native final String getAfterRestartCommand() /*-{
         return this.after_restart_command;
      }-*/;
      
      public native final String getBuiltPackagePath() /*-{
         return this.built_package_path;
      }-*/;
   }

   public interface Handler extends EventHandler
   {
      void onBuildCompleted(BuildCompletedEvent event);
   }

   public BuildCompletedEvent(Data data)
   {
      data_ = data;
   }

   public boolean getRestartR()
   {
      return data_.getRestartR();
   }

   public String getAfterRestartCommand()
   {
      return data_.getAfterRestartCommand();
   }
   
   public String getBuiltPackagePath()
   {
      return data_.getBuiltPackagePath();
   }

   @Override
   public Type<Handler> getAssociatedType()
   {
      return TYPE;
   }

   @Override
   protected void dispatch(Handler handler)
   {
      handler.onBuildCompleted(this);
   }

   private final Data data_;

   public static final Type<Handler> TYPE = new Type<>();
}
