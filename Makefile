

# create the container with postgres
compose-up-dev:
	docker-compose -f ./docker/development/docker-compose.yaml up

# destroy the container with postgres
compose-down-dev:
	docker-compose -f ./docker/development/docker-compose.yaml down

# build image
compose-build-local:
	docker-compose -f ./docker/local/docker-compose.yaml build --no-cache

# create the container with postgres
compose-up-local:
	docker-compose -f ./docker/local/docker-compose.yaml up

# destroy the container with postgres
compose-down-local:
	docker-compose -f ./docker/local/docker-compose.yaml down

# Start the application locally using postgres docker database
start: compose-down-local compose-build-local compose-up-local

# Start the application locally using postgres docker database
start-dev-env: compose-down-dev compose-up-dev

## show log
log-local:
	docker-compose --file ./docker/local/docker-compose.yaml logs -f posterr-app