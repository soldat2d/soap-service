curl --header "content-type: text/xml" -d @request_all_users.xml http://localhost:8080/ws > output.xml
xmllint --format output.xml
curl --header "content-type: text/xml" -d @request_user.xml http://localhost:8080/ws > output.xml
xmllint --format output.xml
curl --header "content-type: text/xml" -d @request_delete_user.xml http://localhost:8080/ws > output.xml
xmllint --format output.xml
curl --header "content-type: text/xml" -d @request_all_users.xml http://localhost:8080/ws > output.xml
xmllint --format output.xml
curl --header "content-type: text/xml" -d @request_add_user.xml http://localhost:8080/ws > output.xml
xmllint --format output.xml
curl --header "content-type: text/xml" -d @request_all_users.xml http://localhost:8080/ws > output.xml
xmllint --format output.xml
curl --header "content-type: text/xml" -d @request_user2.xml http://localhost:8080/ws > output.xml
xmllint --format output.xml
curl --header "content-type: text/xml" -d @request_update_user.xml http://localhost:8080/ws > output.xml
xmllint --format output.xml
curl --header "content-type: text/xml" -d @request_user2.xml http://localhost:8080/ws > output.xml
xmllint --format output.xml
curl --header "content-type: text/xml" -d @request_all_users.xml http://localhost:8080/ws > output.xml
xmllint --format output.xml
