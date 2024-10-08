/*
 * WorkbenchPane.java
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
package org.rstudio.studio.client.workbench.ui;

import java.util.ArrayList;

import org.rstudio.core.client.Debug;
import org.rstudio.core.client.StringUtil;
import org.rstudio.core.client.dom.DomUtils;
import org.rstudio.studio.client.application.events.EventBus;
import org.rstudio.studio.client.workbench.WorkbenchView;
import org.rstudio.studio.client.workbench.events.ActivatePaneEvent;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;

public abstract class WorkbenchPane extends ToolbarPane
                                 implements WorkbenchView,
                                            WorkbenchTab
{
   protected WorkbenchPane(String title)
   {
      title_ = title;
      events_ = null;
   }

   protected WorkbenchPane(String title, EventBus events)
   {
      title_ = title;
      events_ = events;
   }

   public void prefetch(Command continuation)
   {
      continuation.execute();
   }

   public String getTitle()
   {
      return title_;
   }

   // hook for subclasses to be notified right before & after they are selected
   public void onBeforeUnselected()
   {
   }
   public void onBeforeSelected()
   {
   }
   public void onSelected()
   {
   }

   @Override
   public void setFocus()
   {
      ArrayList<Element> focusableElements = DomUtils.getFocusableElements(getElement());
      if (!focusableElements.isEmpty())
      {
         Element el = focusableElements.get(0);
         el.focus();
      }
      else
         Debug.logWarning("Could not set focus, no focusable element on " + title_ + " pane");
   }

   @Override
   public boolean isSuppressed()
   {
      return false;
   }

   @Override
   public boolean closeable()
   {
      return false;
   }

   @Override
   public void confirmClose(Command onConfirmed)
   {
      onConfirmed.execute();
   }

   @Override
   public void bringToFront()
   {
      if (events_ != null && !StringUtil.isNullOrEmpty(title_))
         events_.fireEvent(new ActivatePaneEvent(title_));
      super.bringToFront();
   }

   private final String title_;
   protected final EventBus events_;
}
