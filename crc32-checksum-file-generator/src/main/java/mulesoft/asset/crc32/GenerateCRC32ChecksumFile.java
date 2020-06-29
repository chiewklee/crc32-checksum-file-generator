package mulesoft.asset.crc32;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import jonelo.jacksum.algorithm.Cksum;

public class GenerateCRC32ChecksumFile {

	public static void main(String[] args) throws IOException {

		//test the create checksum file method, change the test file and path accordingly.
		createChecksumFile("test.txt", "src/main/resources/test-files", "src/main/resources/test-files");


	}

	//create the checksum file in the output path
	public static void createChecksumFile(String orgFilename, String inputPath, String outputPath) {
		
		String pathname = inputPath+"/"+orgFilename;
		
		String auditFilename = orgFilename+".aud";
		String auditPathfile = outputPath+"/"+auditFilename;
		
		BufferedWriter output;
		String crc32Value;
		
		output = null;
		
		String fileSize = getFileSizeInBytes(pathname); 
		
	
		try {
		
			crc32Value = getChecksumStr(orgFilename, inputPath);
			createCRC32file(output, crc32Value, fileSize, orgFilename, auditPathfile);
		
		} catch (IOException e) {
			System.out.println("check sum file exception thrown: " +e);
			e.printStackTrace();
		} 
		
	}

	//get the checksum string
	private static String getChecksumStr(String orgFilename, String inputPath) throws IOException {
		
		String pathname = inputPath+"/"+orgFilename;
		
		
		InputStream inputStream = null;
		
			
		
		return getCRC32valueFromInputFile(pathname, inputStream);
		
				
	}

	private static void createCRC32file(BufferedWriter output, String crc32Value, String fileSize, String orgFilename, String auditPathfile) throws IOException {
		
		String auditFileContent = crc32Value +" "+ fileSize + " " + orgFilename; 
		System.out.println("Checksum file Content --- "+auditFileContent);
		
		File auditFile = new File(auditPathfile);
	
		output = new BufferedWriter(new FileWriter(auditFile));
		output.write(auditFileContent);	
		
		System.out.println("cksum 7 ==== Cksum file generated");
		output.close();
	}

	private static String getCRC32valueFromInputFile(String pathname, InputStream inputStream) throws IOException {
		
		System.out.println("cksum 1 ==== Final file initiated");
		
		inputStream = new FileInputStream(pathname);
		
		
		System.out.println("cksum 2 ==== final file converted to inputstream");

		Cksum crc = new Cksum();
		int cnt;
		System.out.println("cksum 3 ==== CRC32 initiated");
		
		while ((cnt = inputStream.read()) != -1) {
				crc.update(cnt);
			   }
		inputStream.close();
		
		System.out.println("cksum 4 ==== CRC32 completed and Inputstream closed");
		
		return String.valueOf(crc.getValue());
	 
	}

	private static String getFileSizeInBytes(String pathname) {
		File mainFile = new File(pathname);
		return String.valueOf(mainFile.length());
	}

}
