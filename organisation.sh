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

idCourse1FRAN1=1
idCourse2FRAN2=2
idCourse3BDON=3
idCourse4ALGO=4

idClass1=1
idClass2=2
idClass3=3
idClass4=4
idClass5=5

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

curl -X PUT "http://localhost:8081/organisation/subject/$idSubject6FRAN/course/$idCourse1FRAN1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/subject/$idSubject6FRAN/course/$idCourse2FRAN2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/subject/$idSubject2BDON/course/$idCourse3BDON" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/subject/$idSubject1ALGO/course/$idCourse4ALGO" -H "accept: application/json" -H "Authorization: $jwt"

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

curl -X PUT "http://localhost:8081/organisation/course/$idCourse1FRAN1/teacher/$idTeacher1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse2FRAN2/teacher/$idTeacher1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse3BDON/teacher/$idTeacher2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse4ALGO/teacher/$idTeacher3" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nLink students to a class\n"

echo -e "\nClass1 with all students\n"

curl -X PUT "http://localhost:8081/organisation/class/$idClass1/student/$idStudent1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass1/student/$idStudent2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass1/student/$idStudent3" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass1/student/$idStudent5" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nClass2 students 1-2-3\n"

curl -X PUT "http://localhost:8081/organisation/class/$idClass2/student/$idStudent1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass2/student/$idStudent2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass2/student/$idStudent3" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nClass3 students 4-5\n"

curl -X PUT "http://localhost:8081/organisation/class/$idClass3/student/$idStudent4" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass3/student/$idStudent5" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nClass4 students 4\n"

curl -X PUT "http://localhost:8081/organisation/class/$idClass4/student/$idStudent4" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nClass5 students 1-3-5\n"

curl -X PUT "http://localhost:8081/organisation/class/$idClass5/student/$idStudent1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass5/student/$idStudent3" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/class/$idClass5/student/$idStudent5" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nLink classes to courses\n"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse1FRAN1/class/$idClass2" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse2FRAN2/class/$idClass3" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse3BDON/class/$idClass5" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse3BDON/class/$idClass4" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse4ALGO/class/$idClass1" -H "accept: application/json" -H "Authorization: $jwt"

curl -X PUT "http://localhost:8081/organisation/course/$idCourse4ALGO/class/$idClass4" -H "accept: application/json" -H "Authorization: $jwt"

echo -e "\nlogout\n"

curl -X POST "http://localhost:8081/auth/logout" -H "accept: application/json" -H "Authorization: $jwt" -d ""