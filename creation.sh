#!/bin/bash


jwt=$(curl -X POST "http://localhost:8081/auth/login" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"password\": \"password\", \"usernameOrEmail\": \"admin\"}" | jq -r '.jwt')

echo "teacher registration"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"teacher@number1.com\", \"firstname\": \"teacher\", \"lastname\": \"number1\", \"password\": \"1234\", \"role\": \"ROLE_TEACHER\", \"username\": \"t1\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"teacher@number2.com\", \"firstname\": \"teacher\", \"lastname\": \"number2\", \"password\": \"1234\", \"role\": \"ROLE_TEACHER\", \"username\": \"t2\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"teacher@number3.com\", \"firstname\": \"teacher\", \"lastname\": \"number3\", \"password\": \"1234\", \"role\": \"ROLE_TEACHER\", \"username\": \"t3\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"teacher@number4.com\", \"firstname\": \"teacher\", \"lastname\": \"number4\", \"password\": \"1234\", \"role\": \"ROLE_TEACHER\", \"username\": \"t4\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"teacher@number5.com\", \"firstname\": \"teacher\", \"lastname\": \"number5\", \"password\": \"1234\", \"role\": \"ROLE_TEACHER\", \"username\": \"t5\"}"

echo "student registration"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number1.com\", \"firstname\": \"student\", \"lastname\": \"number1\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s1\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number2.com\", \"firstname\": \"student\", \"lastname\": \"number2\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s2\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number3.com\", \"firstname\": \"student\", \"lastname\": \"number3\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s3\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number4.com\", \"firstname\": \"student\", \"lastname\": \"number4\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s4\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number5.com\", \"firstname\": \"student\", \"lastname\": \"number5\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s5\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number6.com\", \"firstname\": \"student\", \"lastname\": \"number6\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s6\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number7.com\", \"firstname\": \"student\", \"lastname\": \"number7\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s7\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number8.com\", \"firstname\": \"student\", \"lastname\": \"number8\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s8\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number9.com\", \"firstname\": \"student\", \"lastname\": \"number9\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s9\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number10.com\", \"firstname\": \"student\", \"lastname\": \"number10\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s10\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number11.com\", \"firstname\": \"student\", \"lastname\": \"number11\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s11\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number12.com\", \"firstname\": \"student\", \"lastname\": \"number12\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s12\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number13.com\", \"firstname\": \"student\", \"lastname\": \"number13\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s13\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number14.com\", \"firstname\": \"student\", \"lastname\": \"number14\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s14\"}"

curl -X POST "http://localhost:8081/auth/register" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"email\": \"student@number15.com\", \"firstname\": \"student\", \"lastname\": \"number15\", \"password\": \"1234\", \"role\": \"ROLE_STUDENT\", \"username\": \"s15\"}"

echo "creation of modules"

curl -X POST "http://localhost:8081/creation/module" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"CGES\", \"description\": \"description de CGES\", \"name\": \"Complement de gestion\", \"number\": 150, \"period_number\": 285}"

curl -X POST "http://localhost:8081/creation/module" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"ALD1\", \"description\": \"description de ALD1\", \"name\": \"Algorithmique et gestion des donnees 1\", \"number\": 162, \"period_number\": 170}"

curl -X POST "http://localhost:8081/creation/module" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"BRIT1\", \"description\": \"description de BRIT1\", \"name\": \"Branches instrumentales 1\", \"number\": 152, \"period_number\": 152}"

echo "creation of subjects"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"CPTA\", \"name\": \"Bases de comptabilite\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"IDROI\", \"name\": \"Introduction au droit\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"CGEF\", \"name\": \"Complements de gestion financiere\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"MARK\", \"name\": \"Marketing et Gestion commerciale\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"BECO\", \"name\": \"Bases d economie\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"ALGO\", \"name\": \"Algorithmique et programmation\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"BDON\", \"name\": \"Bases de donnees\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"MDLD\", \"name\": \"Modelisation des donnees\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"UML\", \"name\": \"Modelisation unifiee\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"TABL\", \"name\": \"Resolution tableur\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"FRAN\", \"name\": \"Francais et communication\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"ANGL\", \"name\": \"Anglais\"}"

curl -X POST "http://localhost:8081/creation/subject" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"abbreviation\": \"MATH\", \"name\": \"Mathematiques\"}"

echo "creation of holidays"

curl -X POST "http://localhost:8081/creation/holiday" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"end\": \"2022-07-17\", \"start\": \"2022-07-11\", \"text\": \"vacances test\"}"

echo "creation of courses"

curl -X POST "http://localhost:8081/creation/course" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"end\": \"2022-07-31\", \"name\": \"cours pour CGEF 1\", \"periodsOfTheWeek\": [ 2, 3, 38, 39 ], \"start\": \"2022-07-01\"}"

curl -X POST "http://localhost:8081/creation/course" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"end\": \"2022-07-31\", \"name\": \"cours pour CGEF 2\", \"periodsOfTheWeek\": [ 7, 8, 21, 22 ], \"start\": \"2022-07-01\"}"

curl -X POST "http://localhost:8081/creation/course" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"end\": \"2022-07-31\", \"name\": \"cours pour FRAN\", \"periodsOfTheWeek\": [ 11, 12, 13, 14 ], \"start\": \"2022-07-01\"}"

curl -X POST "http://localhost:8081/creation/course" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"end\": \"2022-07-31\", \"name\": \"cours pour MATH\", \"periodsOfTheWeek\": [ 17, 18, 19, 20 ], \"start\": \"2022-07-01\"}"

echo "creation des groupes d'élèves/classes"

curl -X POST "http://localhost:8081/creation/class" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"comment\": \"commentaires classe 1\", \"name\": \"classe 1\"}"

curl -X POST "http://localhost:8081/creation/class" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"comment\": \"commentaires classe 2\", \"name\": \"classe 2\"}"

curl -X POST "http://localhost:8081/creation/class" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"comment\": \"commentaires classe 3\", \"name\": \"classe 3\"}"

curl -X POST "http://localhost:8081/creation/class" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"comment\": \"commentaires classe 4\", \"name\": \"classe 4\"}"

curl -X POST "http://localhost:8081/creation/class" -H "accept: application/json" -H "Authorization: $jwt" -H "Content-Type: application/json" -d "{ \"comment\": \"commentaires classe 5\", \"name\": \"classe 5\"}"

echo "logout"

curl -X POST "http://localhost:8081/auth/logout" -H "accept: application/json" -H "Authorization: $jwt" -d ""