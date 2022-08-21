start:
	mvn clean package
	docker-compose up --build -d api
	make logs

down:
	docker-compose down

stop:
	docker-compose stop

logs:
	docker-compose logs -f api