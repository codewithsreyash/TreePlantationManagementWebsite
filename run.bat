@echo off
echo =======================================================
echo Starting Tree Plantation App using Maven Wrapper...
echo =======================================================
echo.
echo The application will start in the terminal.
echo Once started, open your browser and navigate to:
echo http://localhost:8080/TreePlantationApp/
echo.
echo To stop the server, press Ctrl+C
echo.

call mvnw.cmd clean jetty:run

pause
