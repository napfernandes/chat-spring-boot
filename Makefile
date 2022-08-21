start:
	docker-compose up --build -d api

down:
	docker-compose down

stop:
	docker-compose stop

logs:
	docker-compose logs -f api