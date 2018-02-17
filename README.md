# my-tee

## Usage
`java -jar MyTee-1.0.jar [-a] [-f file...]`
### Arguments:
* `-a | --append` – Appends the output to the end of file instead of writing over it
* `-f | --files`  – One or more files that will receive the output
### Example:
`ls -l | java -jar MyTee-1.0.jar -a -f file1.txt | wc -l`

## How to build
1. `cd /project/root/dir`
2. `gradle build` -- this creates `MyTee-1.0.jar` in the project's root dir
