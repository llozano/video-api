# video-api
Serves five of the most recent videos published in a youtube channel 

#### Requirements

Java version 23.x should be installed locally *OR* Running Docker locally


### Prepare

- Go to the project directory. e.g. 
	```cd ~/dev/videoapi```
- Install dependencies by running: 
	```./gradlew assemble --refresh-dependencies```
- Build the project. 
	```./gradlew build```

### Run locally
Run the command. 
	```./gradlew bootRun```

### Docker
- Make sure Docker Desktop is running in your computer.
- Build the docker image. 
	```docker build -t video/api .```
- Lauch container. 
	```docker run -p 8080:8080 video/api .```
- IMPORTANT: For Apple Silicon (M1..M4..) use the following command instead:
	```docker run -p 8080:8080 -e JAVA_TOOL_OPTIONS="-XX:UseSVE=0" video/api .```

### Free test

```bash
curl -X GET "http://localhost:8080/api/v1/videos/UCYzPXprvl5Y-Sf0g4vX-m6g" -v
```



