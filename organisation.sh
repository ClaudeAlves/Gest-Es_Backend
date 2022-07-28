#!/bin/bash

idTeacher1=2
idTeacher2=3
idTeacher3=4

idStudent1=5
idStudent2=6
idStudent3=7
idStudent4=8
idStudent5=9

idModule1ALD1=1
idModule2BRIT1=2

idSubject1ALGO=1
idSubject2BDON=2
idSubject3MDLD=3
idSubject4UML=4
idSubject5TABL=5
idSubject6FRAN=6
idSubject7ANGL=7
idSubject8MATH=8

idCourse1MATH1=1
idCourse2ANGL1=2
idCourse3FRAN1=3
idCourse4FRAN2=4
idCourse5BDON1=5
idCourse6ALGO1=6

idClass1=1
idClass2=2
idClass3=3

echo -e "getting jwt token from an admin\n"

jwt=$(curl -X POST "http://localhost:8081/auth/login" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"password\": \"password\", \"usernameOrEmail\": \"admin\"}" | jq -r '.jwt')

echo -e "\nLink modules to their subjects\n"

curl -X PUT "http://localhost:8081/organisation/module/${idModule1ALD1}/subject/${idSubject1ALGO}" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/${idModule1ALD1}/subject/${idSubject2BDON}" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/${idModule1ALD1}/subject/${idSubject3MDLD}" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/${idModule1ALD1}/subject/${idSubject4UML}" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/${idModule1ALD1}/subject/${idSubject5TABL}" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/${idModule2BRIT1}/subject/${idSubject6FRAN}" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/${idModule2BRIT1}/subject/${idSubject7ANGL}" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/${idModule2BRIT1}/subject/${idSubject8MATH}" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nLink subject to their courses\n"

curl -X PUT "http://localhost:8081/organisation/subject/$idSubject8MATH/course/$idCourse1MATH1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/subject/$idSubject7ANGL/course/$idCourse2ANGL1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/subject/$idSubject6FRAN/course/$idCourse3FRAN1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/subject/$idSubject6FRAN/course/$idCourse4FRAN2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/subject/$idSubject2BDON/course/$idCourse5BDON1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/subject/$idSubject1ALGO/course/$idCourse6ALGO1" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nLink student to module -> also links student to subjects\n"

curl -X PUT "http://localhost:8081/organisation/module/$idModule1ALD1/student/$idStudent1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/$idModule2BRIT1/student/$idStudent1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/$idModule1ALD1/student/$idStudent2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/$idModule2BRIT1/student/$idStudent2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/$idModule1ALD1/student/$idStudent3" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/$idModule2BRIT1/student/$idStudent3" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/$idModule1ALD1/student/$idStudent4" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/module/$idModule2BRIT1/student/$idStudent5" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nLink teacher to a course\n" 

curl -X PUT "http://localhost:8081/organisation/course/$idCourse3FRAN1/teacher/$idTeacher1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse4FRAN2/teacher/$idTeacher1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse1MATH1/teacher/$idTeacher2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse2ANGL1/teacher/$idTeacher3" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse5BDON1/teacher/$idTeacher2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse6ALGO1/teacher/$idTeacher3" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nLink students to a class\n"

echo -e "\nClass1 students 1-2-3-4-5\n"

curl -X PUT "http://localhost:8081/organisation/class/$idClass1/student/$idStudent1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass1/student/$idStudent2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass1/student/$idStudent3" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass1/student/$idStudent4" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass1/student/$idStudent5" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nClass2 students 1-2-3\n"

curl -X PUT "http://localhost:8081/organisation/class/$idClass2/student/$idStudent1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass2/student/$idStudent2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass2/student/$idStudent3" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nClass3 students 4-5\n"

curl -X PUT "http://localhost:8081/organisation/class/$idClass3/student/$idStudent4" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass3/student/$idStudent5" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nLink classes to courses\n"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse1MATH1/class/$idClass1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse2ANGL1/class/$idClass1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse3FRAN1/class/$idClass2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse4FRAN2/class/$idClass3" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse5BDON1/class/$idClass1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse6ALGO1/class/$idClass1" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nlogout\n"

curl -X POST "http://localhost:8081/auth/logout" -H "accept: application/json" -H "Authorization: $jwt" -d ""