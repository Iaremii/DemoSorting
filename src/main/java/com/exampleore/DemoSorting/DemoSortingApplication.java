package com.exampleore.DemoSorting;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSortingApplication {

	static int counter = 0;

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(DemoSortingApplication.class, args);
		createStructure(new String[] { "HOME", "DEV", "TEST" }, "count.txt");
		startLisning();

	}

	public static void createStructure(String[] forlders, String counter) throws IOException {
		for (int i = 0; i < forlders.length; i++) {
			File file = new File(forlders[i]);
			if (!file.exists()) {
				if (file.mkdir()) {
					System.out.println("Directory " + forlders[i] + " is created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			}
		}
		File file = new File("HOME\\" + counter).getAbsoluteFile();
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		System.out.println("File count.txt is created!");

	}

	public static void startLisning() throws IOException, InterruptedException {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		//final Path path = Paths.get("C:\\Users\\nazarii.iaremii\\Documents\\eclipse-workspace\\DemoSorting\\HOME");
		final Path path = Paths.get("HOME").toAbsolutePath();
		
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				checkType(event.context().toString());
			}
			key.reset();
		}

	}

	private static void checkType(String string) throws IOException {

		Pattern p = Pattern.compile("\\.jar|\\.xml");
		Matcher m = p.matcher(string);

		if (m.find()) {
			counter++;
			String out = "Number of transferred files: " + counter;
			List<String> txt = Arrays.asList(out);
			Path file = Paths
					.get("HOME\\count.txt").toAbsolutePath();
			System.out.println(file);
			Files.write(file, txt, Charset.forName("UTF-8"));

			Path homePath = Paths
					.get("HOME\\" + string).toAbsolutePath();

			Path devPath = Paths
					.get("DEV\\" + string).toAbsolutePath();

			Path testPath = Paths
					.get("TEST\\" + string).toAbsolutePath();
			BasicFileAttributes attr = Files.readAttributes(homePath, BasicFileAttributes.class);

			try {
				if (attr.creationTime().toMillis() % 2 == 0) {
					System.out.println("File -> " + string + " was moved in folder DEV");
					Files.move(homePath, devPath);
				} else {
					System.out.println("File -> " + string + " was moved in folder TEST");
					Files.move(homePath, testPath);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
