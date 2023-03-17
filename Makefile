docker:
	./mvnw clean
	./mvnw compile
	./mvnw package -Dmaven.test.skip
	docker buildx build . --platform linux/amd64,linux/arm64 -t ghcr.io/helpet-inc/account-service:0.0.1-SNAPSHOT --push