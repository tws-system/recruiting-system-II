TRUNCATE homeworkQuizItem;
UPDATE homeworkQuiz SET evaluateScript="./homework-script/check-readme.sh",description="创建一个文件,文件名为readme.md" WHERE id = 1;