@echo off
cd /d C:\Users\Alidjan\IdeaProjects\OOP2\CourseProject\Course_Project\Tickets

if exist out rmdir /s /q out
mkdir out

dir /s /b src\*.java > sources.txt

javac -encoding UTF-8 -d out @sources.txt

if errorlevel 1 (
    echo.
    echo Ima greshka pri kompiliraneto.
    pause
    exit /b
)

java -cp out bg.tu_varna.f24621658.sit.app.Main

pause