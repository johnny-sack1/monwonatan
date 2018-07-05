STUDENT STORE: 

To be able to test buying mechanics, it was necessary to refactor code. 
DAO objects were move from being initialise inside a methods body, to class fields. 
Also, private method update backpack has been joined with buy method, as the only purpose of public method buy, was to invoke private method update backpack. 

`createEmptyBackpack(String login)`

Creates empty backpack for testing purpose;

@return Backpack

@param login of student who owns backpack

`Student createTestStudentWithSufficientBallance()`

Creates User (Student instance) for testing purpose;
set user ballance as 2000 coolcoins, and connect created student with empty backack.

@return Student

`Student createTestStudentWithInsufficientBallance()`
	
Creates User (Student instance)  for testing purpose;
set user ballance as 0 coolcoins, and connect created student with 	empty backpack.

@return Student
 
`Artifact createTestArtifact()`

Creates Artifact for testing purpose; sets price as 1000 coolcoins.

@return Artifact

`Backpack createBackpackWithTestArtifact(String login)`
	
Creates backpack with test artifact inside, for comparing purpose;

@return Backpack

@param login of student who owns backpack


`testBuySufficientBallance()`

Test if it's possible to buy Artifact when Student have sufficient amount of coolcoins.
It is using Test User from createTestStudentWithSufficientBalance() 	method,
tries to buy Test artifact and compare if it's being add to his backpack.

Backpack state after transaction attempt is being captured from BackpackDAO.

`void testBuyInsufficientBallance()`


Test if it's possible to buy Artifact when Student have insufficient amount of coolcoins.
It is using Test User from   createTestStudentWithInsufficientBalance() method, and
tries to buy Test artifact. Test checks if balance is the same as it was before transaction
attempt, and also check if backpack is still empty.
Backpack state after transaction attempt is being captured from 	BackpackDAO.

`void testParseStudentData()`

Test parseStudentData method from StudentProfile controller.
It receives testing String (formData1) and compares map returned from parseStudentData() method with map (expectedData - with correctly parsed user data).
 

`void testParseStudentDataWithEmptyForm()`

Test behaviour of parseStudentData method when it receives form without any personal data. It doesn't generate any exception, instead it returns empty map.





