/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousedatacollector;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MouseDataCollertor implements NativeMouseInputListener , NativeMouseWheelListener  {
        long startTime = System.currentTimeMillis();
        private FileWriter file;
        
        
        
        
        
        public void stop(){
            try {
                GlobalScreen.unregisterNativeHook();
                this.finalize();
            } catch (NativeHookException ex) {
                Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
        
        public void setWrite(FileWriter file){
            this.file=file;
        }
        public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
            long estimatedTime = System.currentTimeMillis() - startTime;
            try {
                file.append(estimatedTime+","+"Nobutton,Scrool,"+e.getX()+","+e.getX()+"\n");
                file.flush();
            } catch (IOException ex) {
                Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

    
	public void nativeMouseClicked(NativeMouseEvent e) {
	}

	public void nativeMousePressed(NativeMouseEvent e) {
                if (e.getButton() == 1){
                    long estimatedTime = System.currentTimeMillis() - startTime;
                    try {
                        file.append(estimatedTime+","+"Left,Pressed,"+e.getX()+","+e.getY()+"\n");
                        file.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (e.getButton()==2){
                    long estimatedTime = System.currentTimeMillis() - startTime;
                    try {
                        file.append(estimatedTime+","+"Right,Pressed,"+e.getX()+","+e.getY()+"\n");
                        file.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
	}

	public void nativeMouseReleased(NativeMouseEvent e) {
                if (e.getButton() == 1){
                    long estimatedTime = System.currentTimeMillis() - startTime;
                    try {
                        file.append(estimatedTime+","+"Left,Released,"+e.getX()+","+e.getY()+"\n");
                        file.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (e.getButton()==2){
                    long estimatedTime = System.currentTimeMillis() - startTime;
                    try {
                        file.append(estimatedTime+","+"Right,Released,"+e.getX()+","+e.getY()+"\n");
                        file.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
                long estimatedTime = System.currentTimeMillis() - startTime;
            try {
                file.append(estimatedTime+","+"NoButton,Move,"+e.getX()+","+e.getY()+"\n");
                file.flush();
            } catch (IOException ex) {
                Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	public void nativeMouseDragged(NativeMouseEvent e) {
                long estimatedTime = System.currentTimeMillis() - startTime;
                try {
                file.append(estimatedTime+","+"NoButton,Drag,"+e.getX()+","+e.getY()+"\n");
                file.flush();
            } catch (IOException ex) {
                Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

//	public static void main(String[] args) {
//            
//                DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
//                Date date = new Date();
//               
//                String s = dateFormat.format(date);
//            try{
//                FileWriter files = new FileWriter(s+".CSV");
//                files.append("client timestamp,button,state,x,y\n");
//            
//               try {
//                GlobalScreen.registerNativeHook();
//                Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
//                logger.setLevel(Level.OFF);
//
//                // Don't forget to disable the parent handlers.
//                logger.setUseParentHandlers(false);
//                }
//                catch (NativeHookException ex) {
//                System.err.println("There was a problem registering the native hook.");
//                System.err.println(ex.getMessage());
//                
//                System.exit(1);
//                }
//                
//                // Construct the example object.
//                MouseDataCollertor example = new MouseDataCollertor();
//                example.setWrite(files);
//                // Add the appropriate listeners.
//                GlobalScreen.addNativeMouseListener(example);
//                GlobalScreen.addNativeMouseMotionListener(example);
//                GlobalScreen.addNativeMouseWheelListener(example);
//                GlobalScreen.unregisterNativeHook();
//                }
//
//        catch (IOException ex) {
//                Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (NativeHookException ex) {
//                Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
//            }
//}
        }
                


