/**
 * Ingress Version Extractor
 * 
 * This Application extracts the specific key form the Ingress-Apk,
 * which can be used to spoof the right/up-to-date version-string used for the login-process.
 * 
 *  by XWaVeX (xwavedw@gmail.com).
 */
package de.dlw.ingress.version.extractor.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IVEMain {
	private String keyStorage = null;

	/**
	 * @param args
	 *            [0] -> apkFilePath = i.e. "res/ingress_version_1.33.apk" [1]
	 *            -> releaseDate = i.e. "2013-08"
	 */
	public static void main(String[] args) {
		if (args.length == 2)
			new IVEMain(args[0], args[1], false, null);
		else {
			System.err.println("Parameters do NOT match. Have to be == 2");
			System.out.println();
			System.out.println();
			System.out
					.println("Usage: IngressVersionExtractor <apkFilePath> <releaseDate>");
			System.out.println();
			System.out.println("\tExample:");
			System.out
					.println("\t\t<apkFilePath>\tres/ingress_version_1.33.apk");
			System.out.println("\t\t<releaseDate>\t2013-08");
			System.out.println();
			System.out.println();
			System.out.println("by Dennis Leroy Wigand");
		}
	}

	public IVEMain(String apkFileName, String datePhrase, boolean silent,
			String alternateApkTool) {
		System.out.println("IngressVersionExtractor");
		System.out.println("\tby Dennis Leroy Wigand");
		System.out.println();
		System.out.println("Extracting Key with: " + datePhrase + " from "
				+ apkFileName);
		Process proc;
		BufferedReader error = null;
		try {
			String apkToolPath = "res/apktool.jar";
			if ((alternateApkTool != null) && (alternateApkTool != ""))
				apkToolPath = alternateApkTool;

			proc = Runtime.getRuntime().exec(
					"java -jar " + apkToolPath + " d " + apkFileName
							+ " res/tmp_folder");
			error = new BufferedReader(new InputStreamReader(
					proc.getErrorStream()));
			proc.waitFor();
		} catch (IOException e) {
			if (alternateApkTool == null)
				System.err.println("Check if res/apktool.jar exists.");
			else
				System.err.println("Check if " + alternateApkTool + " exists.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Handle error while decompiling.
		String resultOutput;
		try {
			while ((resultOutput = error.readLine()) != null) {
				if (resultOutput.startsWith("E"))
					System.err.println(resultOutput);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Search files for catch-phrase.
		File dir = new File("res/tmp_folder");
		String key;
		if ((dir != null) && (dir.exists()) && (dir.isDirectory())) {
			ArrayList<String> files = new ArrayList<String>();
			try {
				files = (ArrayList<String>) searchFiles(dir, datePhrase + ".*",
						files);
				for (String tmp : files) {
					if ((tmp != null) && (tmp != "") && (tmp.contains(" opt"))) {
						int one = tmp.lastIndexOf(" opt");
						key = tmp.substring(0, one + 4);
						keyStorage = key;
						System.out.println("\n\t" + key);
						deleteDirectory(dir);
						if (silent)
							saveKeyToFile(key);
						return;
					}
				}
			} catch (FileNotFoundException e1) {
				System.err.println("Files have not been found.");
			}
		}
		if ((keyStorage == null) || (keyStorage == "")) {
			deleteDirectory(dir);
			System.err
					.println("Key could not be extracted. -> Try a different date.");
		}
	}

	private void saveKeyToFile(String key) {
		PrintWriter out = null;
		try {
			out = new PrintWriter("res/ingress_version_key.dlw");
			out.println(key.trim());
			System.out.println("\nKey saved in res/ingress_version_key.dlw.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}

	/** Search the content of these files. */
	private List<String> searchFiles(File file, String pattern,
			List<String> result) throws FileNotFoundException {

		if (result == null) {
			result = new ArrayList<String>();
		}

		File[] files = file.listFiles();

		if (files != null) {
			for (File currentFile : files) {
				if (currentFile.isDirectory()) {
					searchFiles(currentFile, pattern, result);
				} else {
					Scanner scanner = new Scanner(currentFile);
					String tmp = scanner.findWithinHorizon(pattern, 0);
					if (tmp != null) {
						result.add(tmp);
					}
					scanner.close();
				}
			}
		}
		return result;
	}

	/** Delete a folder that still contains some files. */
	private boolean deleteDirectory(File dir) {
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (File currentFile : files) {
				if (currentFile.isDirectory()) {
					deleteDirectory(currentFile);
				} else {
					currentFile.delete();
				}
			}
		}
		return (dir.delete());
	}

	public String getKeyStorage() {
		return keyStorage;
	}
}
