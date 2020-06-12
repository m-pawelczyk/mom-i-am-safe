curl --location --request POST 'http://localhost:8080/api/v1/messages' \
--header 'Content-Type: application/json' \
--header 'Content-Type: text/plain' \
--data-raw '{
    "phoneNumber": "500400305",
    "message": "Test message which should be send to Twitter"
}'