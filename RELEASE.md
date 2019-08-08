# Release

Making a release of Processing.R requires git, jdk8, and Ant.

**NOTE:** These instructions do not yet include updating the docs using processing-doc-tools.

## New Release Instructions

1.  Clone the Processing.R repo.
2.  Confirm that jdk8 is installed on the release computer, or install (see below)
3.  Confirm that Ant is installed.
4.  set JAVA_HOME and run deploy.sh from within the scripts folder

    ```
    # ...install jdk8u202, then:
    export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
    cd scripts
    ./deploy.sh 107 v1.0.7 > log.txt
    ```

5.  Confirm jdk8 was used by checking the log.

    ```
    cat log.txt | grep '1.8'
    ```

    The log may show a warning that will also indicate it is correctly using 1.8 (jdk8):

    > build:
    >     [javac] Compiling 34 source files to /Users/username/git/Processing.R/build
    >     [javac] warning: Supported source version 'RELEASE_6' from annotation processor 'org.eclipse.sisu.scanners.index.SisuIndexAPT6' less than -source '1.8'
    >     [javac] Note: Some input files use or override a deprecated API.
    >     [javac] Note: Recompile with -Xlint:deprecation for details.
    >     [javac] 1 warning

6.  Test install of the new mode by unzipping the new docs/RLangMode.zip to the local PDE Processing/modes directory and restarting PDE.

    The mode "R" should appear in the dropdown menu, launch a new R sketch window, and run an R sketch from examples.

7.  Commit the updated docs/RLangMode.txt to the Processing.R repo.
8.  Tag the commit for release (e.g. v1.0.7).
9.  Create a release on GitHub (e.g. v1.0.7).
10. Upload as release attachments and save:

    -  docs/RLangMode.txt
    -  docs/RL:angMode.zip

    Be careful not to take RLangMode.txt from dist/

11. Test download of the release: restart Processing PDE and download via Contributions Manager.


## Installing jdk8

JDK installation notes:

1.  Review Java 

    Processing currently requires jdk8u202.
    
    Oracle has rapidly changed their hosting of jdk,
    making it increasingly difficult. For the latest,
    see Processing Build Instructions:
    
    -  https://github.com/processing/processing/wiki/Build-Instructions

2.  Download jdk8 if not installed

    This may involve creating an Oracle account
    in order to download.
    
    Alternately, download from another source.
    
    For OS X:
    
    -  https://github.com/frekele/oracle-java/releases/download/8u202-b08/jdk-8u202-macosx-x64.dmg

3.  Confirm checksum

    Image matches the published checksum via md5:
    -  https://www.oracle.com/webfolder/s/digest/8u202checksum.html
    
    ```
    md5 jdk-8u202-macosx-x64.dmg 
      MD5 (jdk-8u202-macosx-x64.dmg) = 9eb027c06c5da727229a29b3be79bf50
    ```

4.  Run the graphical installer.

5.  Check the install is present.

    On OS X:

    ```
    $ ls /Library/Java/JavaVirtualMachines/
    jdk-10.0.1.jdk   jdk1.8.0_192.jdk jdk1.8.0_202.jdk
    ```

6.  Set the JAVA_HOME environment variable immediately before running the deploy.sh script.

    On OSX:
    
    ```
    export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
    echo $JAVA_HOME
    /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
    ```
    
    ...or an alternative to the absolute path is the java_home utility:
    
    ```
    export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
    ```


### jdk on homebrew: DEPRECATED

The OS X homebrew cask was the most popular way of installing jdk. However, it has been removed due to changing availability from Oracle.

-  https://github.com/Homebrew/homebrew-cask-versions/issues/7253

These are the old install instructions.

```
brew cask install java8
ls /Library/Java/JavaVirtualMachines/
    jdk-10.0.1.jdk jdk1.8.0_192.jdk
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
```

A new java cask points to OpenJDK / AdoptOpenJDK, and Processing.R mode could move to OpenJDK -- however currently Processing cannot use OpenJDK, and some of the Processing.R test suite builds Processing.
