@echo off
title PENDO Task Organizer
echo ========================================
echo    PENDO - Task Organizer
echo    Iniciando aplicación...
echo ========================================
echo.

REM Verificar si Maven está instalado
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven no está instalado o no está en el PATH
    echo Por favor instala Maven y vuelve a intentar
    pause
    exit /b 1
)

REM Verificar si Java está instalado
java --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java no está instalado o no está en el PATH
    echo Por favor instala Java 17+ y vuelve a intentar
    pause
    exit /b 1
)

echo Maven y Java detectados correctamente
echo Compilando y ejecutando aplicación...
echo.

REM Ejecutar la aplicación
mvn clean javafx:run

if %errorlevel% neq 0 (
    echo.
    echo ERROR: La aplicación falló al ejecutarse
    echo Verifica que todas las dependencias estén correctas
    pause
    exit /b 1
)

echo.
echo Aplicación finalizada correctamente
pause