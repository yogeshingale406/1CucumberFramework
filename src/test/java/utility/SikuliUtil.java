package utility;

import java.io.IOException;

import org.junit.*;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.sikuli.script.SikuliException;




public class SikuliUtil {

	public static Screen screen;
	public static Pattern pattern;
	public static void createScreen() {
		screen= new Screen();
	}

	public static void createPattern(String screenImg){
		pattern= new Pattern(screenImg);
	}
	public static void patternClick(Pattern pattern) {
		try{
			screen.click(pattern);
		}catch(SikuliException t){
			try {
				TestUtil.captureScreenshot();
			}catch (IOException e) {
				Assert.fail(t.getMessage());
				e.printStackTrace();
			}
			Assert.fail(t.getMessage());
			t.printStackTrace();
		}
	}

	public static void patternType(Pattern pattern,String path) {
		try {
			screen.type(pattern,path);
		} catch (SikuliException t) {
			try {
				TestUtil.captureScreenshot();
			}catch (IOException e) {
				Assert.fail(t.getMessage());
				e.printStackTrace();
			}
			Assert.fail(t.getMessage());
			t.printStackTrace();
			t.printStackTrace();
		}
	}

	public static void patternWait(Pattern pattern,int time) {
		try {
			screen.wait(pattern,time);
		} catch (SikuliException t) {
			try {
				TestUtil.captureScreenshot();
			}catch (IOException e) {
				Assert.fail(t.getMessage());
				e.printStackTrace();
			}
			Assert.fail(t.getMessage());
			t.printStackTrace();
			t.printStackTrace();
		}
	}
}
