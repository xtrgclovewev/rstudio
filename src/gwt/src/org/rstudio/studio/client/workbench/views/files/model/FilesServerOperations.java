/*
 * FilesServerOperations.java
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
package org.rstudio.studio.client.workbench.views.files.model;

import java.util.ArrayList;

import org.rstudio.core.client.files.FileSystemItem;
import org.rstudio.studio.client.server.ServerRequestCallback;
import org.rstudio.studio.client.server.Void;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public interface FilesServerOperations
{
   void stat(String path,
             ServerRequestCallback<FileSystemItem> requestCallback);

   void isTextFile(String path,
                   ServerRequestCallback<Boolean> requestCallback);

   void isGitDirectory(String path,
                       ServerRequestCallback<Boolean> requestCallback);

   void isPackageDirectory(String path,
                           ServerRequestCallback<Boolean> requestCallback);

   void getFileContents(String path,
                        String encoding,
                        ServerRequestCallback<String> requestCallback);

   // get a file listing
   void listFiles(FileSystemItem directory,
                  boolean monitor,
                  boolean showHidden,
                  ServerRequestCallback<DirectoryListing> requestCallback);

   void listAllFiles(String path,
                     String pattern,
                     ServerRequestCallback<JsArrayString> requestCallback);

   // create file with contents
   void createFile(FileSystemItem file,
                   String contents,
                   ServerRequestCallback<Void> requestCallback);
   
   // create a folder
   void createFolder(FileSystemItem folder,
                     ServerRequestCallback<Void> requestCallback);

   // delete files
   void deleteFiles(ArrayList<FileSystemItem> files,
                    ServerRequestCallback<Void> requestCallback);

   // copy file
   void copyFile(FileSystemItem sourceFile,
                 FileSystemItem targetFile,
                 boolean overwrite,
                 ServerRequestCallback<Void> requestCallback);

   // move files
   void moveFiles(ArrayList<FileSystemItem> files,
                  FileSystemItem targetDirectory,
                  ServerRequestCallback<Void> requestCallback);

   // rename file
   void renameFile(FileSystemItem file,
                   FileSystemItem targetFile,
                   ServerRequestCallback<Void> serverRequestCallback);
   
   // touch file (create empty file)
   void touchFile(FileSystemItem newFile,
                  ServerRequestCallback<Void> requestCallback);


   String getFileUrl(FileSystemItem file);

   String getFileUploadUrl();

   void completeUpload(FileUploadToken token,
                       boolean commit,
                       ServerRequestCallback<Void> requestCallback);

   String getFileExportUrl(String name,
                           FileSystemItem file);

   String getFileExportUrl(String name,
                           FileSystemItem parentDirectory,
                           ArrayList<String> filenames);

   void writeConfigJSON(String relativePath,
                  JavaScriptObject object,
                  ServerRequestCallback<Boolean> requestCallback);

   void readConfigJSON(String relativePath,
                 boolean logErrorIfNotFound,
                 ServerRequestCallback<JavaScriptObject> requestCallback);

   void getIssueUrl(String id, 
                    ServerRequestCallback<String> requestCallback);

   void makeProjectRelative(JsArrayString paths,
                            ServerRequestCallback<JsArrayString> requestCallback);
   
   /**
    * Use VERY sparingly; we generally don't want to expose
    * non-aliased paths to other parts of the client codebase
    * (most client-side APIs assume paths are aliased)
    */
   String resolveAliasedPath(FileSystemItem file);
}
