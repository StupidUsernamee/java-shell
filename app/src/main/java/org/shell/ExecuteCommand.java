package org.shell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.text.MessageFormat;
import java.util.*;

public class ExecuteCommand{

    private static final List<String> commandList = List.of("exit", "echo", "type", "cd", "ls",
            "mkdir", "pwd", "rm", "rmdir", "touch", "cat", "cp", "mv");

    private static String currentDirectory = System.getProperty("user.dir");;

    public static void execute(String command, String[] args) throws IOException {

        if (command.equals(commandList.get(0))) {
            executeExit(command, args);
        }
        else if (command.equals(commandList.get(1))) {
            executeEcho(args);
        }
        else if (command.equals(commandList.get(2))) {
            executeType(command, args);
        }
        else if (command.equals(commandList.get(3))) {
            executeCd(command, args);
        }
        else if (command.equals(commandList.get(4))) {
            executeLs(command, args);
        }
        else if (command.equals(commandList.get(5))) {
            executeMkdir(command, args);
        }
        else if (command.equals(commandList.get(6))) {
            executePwd();
        }
        else if (command.equals(commandList.get(7))) {
            executeRm(command, args);
        }
        else if (command.equals(commandList.get(8))) {
            executeRmdir(command, args);
        }
        else if (command.equals(commandList.get(9))) {
            executeTouch(command, args);
        }
        else if (command.equals(commandList.get(10))) {
            executeCat(command, args);
        }
        else if (command.equals(commandList.get(11))) {
            executeCp(command, args);
        }
        else if (command.equals(commandList.get(12))) {
            executeMv(command, args);
        }
        else {
            notFoundError(command);
        }
    }

    private static void executeMv(String command, String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: mv <fileToMove> <dirToMove>");
        }
        else {
            File fileToCopy = new File(currentDirectory + File.separator + args[0]);
            File dirToCopy = new File(currentDirectory + File.separator + args[1]);
            if (!dirToCopy.exists() || !dirToCopy.isDirectory()) {
                System.out.println("Directory " + args[1] + " does not exist");
            }
            else if (!fileToCopy.exists() || !fileToCopy.isFile()) {
                System.out.println("File " + args[0] + " does not exist");
            }
            else {
                Files.move(fileToCopy.toPath(), dirToCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private static void executeCp(String command, String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: cp <fileToCopy> <dirToCopy>");
        }
        else {
            File fileToCopy = new File(currentDirectory + File.separator + args[0]);
            File dirToCopy = new File(currentDirectory + File.separator + args[1]);
            if (!dirToCopy.exists() || !dirToCopy.isDirectory()) {
                System.out.println("Directory " + args[1] + " does not exist");
            }
            else if (!fileToCopy.exists() || !fileToCopy.isFile()) {
                System.out.println("File " + args[0] + " does not exist");
            }
            else {
                Files.copy(fileToCopy.toPath(), dirToCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private static void executeCat(String command, String[] args) throws FileNotFoundException {
        File file = new File(currentDirectory + File.separator + args[0]);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }
        //scanner.close();
    }

    private static void executeTouch(String command, String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: touch <fileName>");
        }
        else {
            File file = new File(currentDirectory + File.separator + args[0]);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                System.out.println("File already exists: " + file.getAbsolutePath());
            }
        }
    }


    private static void executeRmdir(String command, String[] args) {
        Path dir = Path.of(currentDirectory + File.separator + args[0]);
        File dirFile = dir.toFile();
        if (dirFile.isDirectory() && checkDirIsEmpty(dir)) {
            dirFile.delete();
        } else if (!checkDirIsEmpty(dir)) {
            System.out.println(MessageFormat.format("Jshell: dir:{0} is not empty and cannot be deleted", dir));
        }
    }


    private static void executeRm(String command, String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: rm <filePath>");
        } else {
            File file = new File(currentDirectory + File.separator + args[0]);
            if (file.exists() && file.isFile()) {
                file.delete();
            } else {
                System.out.println("Jshell: File does not exist: " + args[0]);
            }

        }
    }


    private static void executePwd() {
        System.out.println(currentDirectory);
    }


    private static void executeMkdir(String command, String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: mkdir <directory>");
        }
        else {
            File directory = new File(currentDirectory + File.separator + args[0]);
            if (!directory.exists()) {
                directory.mkdirs();
            } else {
                System.out.println("Jshell: Directory already exists: " + directory.getAbsolutePath());
            }
        }
    }


    private static void executeExit(String command, String[] args) {
        try {
            int exitCode = (args.length > 0) ? Integer.parseInt(args[0]) : 0;
            System.exit(exitCode);
        } catch (NumberFormatException e) {
            inValidParameterError(command, args[0]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static void executeEcho(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: echo <message>");
        } else {
            try {
                for (String s : args) {
                    System.out.print(s + " ");
                }
                System.out.println();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private static void executeType(String command, String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: type <command>");
        } else if (validateCommand(args[0]) && args.length == 1) {
            System.out.println(MessageFormat.format("Jshell: type<{0}>: valid", args[0]));
        }
        else {
            notFoundError(Arrays.toString(args));
        }
    }


    public static void executeCd(String command, String[] args) {
        try {
            Path newPath;
            if (args == null || args.length == 0) {
                newPath = Path.of(System.getProperty("user.home"));
            } else {
                newPath = Path.of(currentDirectory).resolve(args[0]).normalize();
            }

            if (Files.exists(newPath) && Files.isDirectory(newPath)) {
                currentDirectory = newPath.toAbsolutePath().toString();
            } else {
                System.out.println("cd: No such file or directory: " + args[0]);
                return;
            }
            System.out.println(currentDirectory);
        } catch (Exception e) {
            System.out.println("cd: Error: " + e.getMessage());
        }
    }

    public static void executeLs(String command, String[] args) {
        File folder = new File(currentDirectory);
        File[] files = folder.listFiles();
        if (files == null) {
            System.out.println("ls: Cannot access directory");
            return;
        }

        // Sorting: directories first, then files, both in alphabetical order
        List<File> sortedFiles = Arrays.stream(files)
                .sorted(Comparator.comparing((File f) -> !f.isDirectory())
                        .thenComparing(File::getName))
                .toList();

        if (args.length > 0 && "-l".equals(args[0])) {
            for (File f : sortedFiles) {
                try {
                    String permissions = getPermissions(f);
                    long size = f.isFile() ? f.length() : 0;
                    FileTime lastModifiedTime = Files.getLastModifiedTime(f.toPath());
                    System.out.printf("%s %5d %s %s\n", permissions, size, lastModifiedTime, f.getName());
                } catch (Exception e) {
                    System.out.println("ls: Error retrieving file attributes for " + f.getName());
                }
            }
        } else {
            for (File f : sortedFiles) {
                System.out.print(f.getName() + "\t");
            }
            System.out.println();
        }
    }

    private static String getPermissions(File file) throws Exception {
        PosixFileAttributes attr = Files.readAttributes(file.toPath(), PosixFileAttributes.class);
        Set<PosixFilePermission> perms = attr.permissions();
        StringBuilder sb = new StringBuilder();

        sb.append(file.isDirectory() ? 'd' : '-');
        sb.append(perms.contains(PosixFilePermission.OWNER_READ) ? 'r' : '-');
        sb.append(perms.contains(PosixFilePermission.OWNER_WRITE) ? 'w' : '-');
        sb.append(perms.contains(PosixFilePermission.OWNER_EXECUTE) ? 'x' : '-');
        sb.append(perms.contains(PosixFilePermission.GROUP_READ) ? 'r' : '-');
        sb.append(perms.contains(PosixFilePermission.GROUP_WRITE) ? 'w' : '-');
        sb.append(perms.contains(PosixFilePermission.GROUP_EXECUTE) ? 'x' : '-');
        sb.append(perms.contains(PosixFilePermission.OTHERS_READ) ? 'r' : '-');
        sb.append(perms.contains(PosixFilePermission.OTHERS_WRITE) ? 'w' : '-');
        sb.append(perms.contains(PosixFilePermission.OTHERS_EXECUTE) ? 'x' : '-');

        return sb.toString();
    }


    private static boolean checkDirIsEmpty(Path dir) {
        File[] files = dir.toFile().listFiles();
        return files == null || files.length == 0;
    }


    private static boolean validateCommand(String command) {
        return commandList.contains(command);
    }


    private static void notFoundError(String s) {
        System.out.println(MessageFormat.format("Jshell: command not found: {0}", s));
    }


    private static void inValidParameterError(String command, String parameter) {
        System.out.println(MessageFormat.format("Jshell: {0} has no parameter: {1}", command, parameter));
    }
}
