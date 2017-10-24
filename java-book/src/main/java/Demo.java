import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Demo {

	public static void main(String[] args) throws Exception {
		Demo demo = new Demo();
		demo.readZipFile(new File("C:/yrtz/test/2017-09-18.zip"));
	}
	@SuppressWarnings("resource")
	public void readZipFile(File file) throws Exception {
		 if(file==null){
			 return;
		 }
        InputStream in=null;  
        ZipInputStream zin=null;  
        BufferedReader br=null;
        try {
			ZipFile zf = new ZipFile(file);
			in = new BufferedInputStream(new FileInputStream(file));
			zin = new ZipInputStream(in);
			ZipEntry ze;
			br = null;
			while ((ze = zin.getNextEntry()) != null) {
				if (ze.isDirectory()) {
				} else {
					long size = ze.getSize();
					if (size > 0) {
						br = new BufferedReader(new InputStreamReader(
								zf.getInputStream(ze)));
						String line;
						while ((line = br.readLine()) != null) {
							System.out.println(line);
						}
						br.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		zin.closeEntry();
        if(br!=null){
       	 br.close();
        }
        if(zin!=null){
       	 zin.close();
        }
        if(in!=null){
       	 in.close();
        }
        zin.close();
        System.out.println("okok...");
    }

}
