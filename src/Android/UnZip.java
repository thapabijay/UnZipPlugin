package org.exolutus.unzip;

import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.zip.*;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import org.apache.cordova.*;

public class UnZip extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("unzip")) {
            String filename= args.getString(0); 
            this.unzip(message, callbackContext);
            return true;
        }
        return false;
    }

    private void unzip(String zipname, CallbackContext callbackContext) {
        if (zipname != null && zipname.length() > 0) {
	    String path=Environment.getExternalStorageDirectory().getAbsolutePath();
		 path=path+"/Android/data";
         InputStream is;
         ZipInputStream zis;
         try 
         {
             String filename;
             is = new FileInputStream(path+"/" + zipname);
             zis = new ZipInputStream(new BufferedInputStream(is));          
             ZipEntry ze;
             byte[] buffer = new byte[1024];
             int count;
     		
             while ((ze = zis.getNextEntry()) != null) 
             {
            	 Log.e("zipentry",ze.getName());
                 filename = ze.getName();
                 //zip created with c# has backslash
                 filename=filename.replace("\\", "/");
                 Log.e("files",filename);                 
                 File fmd = new File(path +"/"+ filename);
                 Log.e("myFiles",fmd.getName());
                 //won't go to if statement for the zip created with c#
                 if (ze.isDirectory()==true || filename.lastIndexOf('.')==-1) {
	                 fmd.mkdirs();
	                 continue;
                 }else{
                	 new File(fmd.getParent()).mkdir();                	 
                 }
                 FileOutputStream fout = new FileOutputStream(path +"/"+ filename);
                 
                 while ((count = zis.read(buffer)) != -1) 
                 {
                     fout.write(buffer, 0, count);             
                 }
                 fout.close();               
                 zis.closeEntry();
             }
             zis.close();
		callbackContext.success(zipname);
         } 
         catch(IOException e)
         {
            callbackContext.error("Something Wrong Going on !!");
         }
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}

