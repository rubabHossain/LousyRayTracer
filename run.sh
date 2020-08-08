#!/bin/bash
echo "Following script is for Macos -- would be platform agnostic if not for 'open temp.ppm' "
mvn clean package &&
((java -cp target/raytracer-1.0-SNAPSHOT.jar com.raytracer.App) > ./target/temp.ppm) &&
open ./target/temp.ppm
