#!/bin/bash

echo -e "getting jwt token from an admin\n"

jwt=$(curl -X POST "http://localhost:8081/auth/login" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"password\": \"password\", \"usernameOrEmail\": \"admin\"}" | jq -r '.jwt')

echo -e "\nteacher registration\n"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"teacher@number1.com\", \"firstname\": \"teacher\", \"lastname\": \"number1\", \"password\": \"1234\", \"role\": \"ROLE_TEACHER\", \"username\": \"t1\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"teacher@number2.com\", \"firstname\": \"teacher\", \"lastname\": \"number2\", \"password\": \"1234\", \"role\": \"ROLE_TEACHER\", \"username\": \"t2\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"teacher@number3.com\", \"firstname\": \"teacher\", \"lastname\": \"number3\", \"password\": \"1234\", \"role\": \"ROLE_TEACHER\", \"username\": \"t3\"}"

echo -e "\nstudent registration\n"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number1.com\", \"firstname\": \"student\", \"lastname\": \"number1\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s1\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number2.com\", \"firstname\": \"student\", \"lastname\": \"number2\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s2\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number3.com\", \"firstname\": \"student\", \"lastname\": \"number3\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s3\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number4.com\", \"firstname\": \"student\", \"lastname\": \"number4\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s4\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number5.com\", \"firstname\": \"student\", \"lastname\": \"number5\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s5\"}"

echo -e "\ncreation of modules\n"

curl -X POST "http://localhost:8081/creation/module" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"ALD1\", \"description\": \"description de ALD1\", \"name\": \"Algorithmique et gestion des donnees 1\", \"number\": 162, \"period_number\": 170}"

curl -X POST "http://localhost:8081/creation/module" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"BRIT1\", \"description\": \"description de BRIT1\", \"name\": \"Branches instrumentales 1\", \"number\": 152, \"period_number\": 152}"

echo -e "\ncreation of subjects\n"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"ALGO\", \"name\": \"Algorithmique et programmation\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"BDON\", \"name\": \"Bases de donnees\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"MDLD\", \"name\": \"Modelisation des donnees\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"UML\", \"name\": \"Modelisation unifiee\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"TABL\", \"name\": \"Resolution tableur\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"FRAN\", \"name\": \"Francais et communication\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"ANGL\", \"name\": \"Anglais\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"MATH\", \"name\": \"Mathematiques\"}"

echo -e "\ncreation of holidays\n"

curl -X POST "http://localhost:8081/creation/holiday" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"end\": \"2022-07-17\", \"start\": \"2022-07-11\", \"text\": \"vacances test\"}"

echo -e "creation of courses"

curl -X POST "http://localhost:8081/creation/course" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"end\": \"2022-07-31\", \"name\": \"cours pour FRAN 1\", \"periodsOfTheWeek\": [ 2, 3, 38, 39 ], \"start\": \"2022-07-01\"}"

curl -X POST "http://localhost:8081/creation/course" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"end\": \"2022-07-31\", \"name\": \"cours pour FRAN 2\", \"periodsOfTheWeek\": [ 7, 8, 21, 22 ], \"start\": \"2022-07-01\"}"

curl -X POST "http://localhost:8081/creation/course" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"end\": \"2022-07-31\", \"name\": \"cours pour BDON\", \"periodsOfTheWeek\": [ 11, 12, 13, 14 ], \"start\": \"2022-07-01\"}"

curl -X POST "http://localhost:8081/creation/course" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"end\": \"2022-07-31\", \"name\": \"cours pour ALGO\", \"periodsOfTheWeek\": [ 17, 18, 19, 20 ], \"start\": \"2022-07-01\"}"

echo -e "\ncreation des groupes d'élèves/classes\n"

curl -X POST "http://localhost:8081/creation/class" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"comment\": \"commentaires classe 1\", \"name\": \"classe 1\"}"

curl -X POST "http://localhost:8081/creation/class" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"comment\": \"commentaires classe 2\", \"name\": \"classe 2\"}"

curl -X POST "http://localhost:8081/creation/class" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"comment\": \"commentaires classe 3\", \"name\": \"classe 3\"}"

curl -X POST "http://localhost:8081/creation/class" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"comment\": \"commentaires classe 4\", \"name\": \"classe 4\"}"

curl -X POST "http://localhost:8081/creation/class" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"comment\": \"commentaires classe 5\", \"name\": \"classe 5\"}"

echo -e "\nlogout\n"

curl -X POST "http://localhost:8081/auth/logout" -H "accept: application/json" -H "Authorization: $jwt" -d ""