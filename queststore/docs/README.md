## Tests
### Running the tests

Run this command:
```
mvn test
```

### List of tests

- `testIsLoadingArtifact()`
- `testUserAbleToLoginWithCorrectPassword()`
- `testUserIsUnableToLoginWithWrongPassword()`
- `testValidatePassword()`
- `testParseStudentDataWithEmptyForm()`
- `testParseStudentData()`
- `testBuyInsufficientBallance()`
- `testBuySufficientBallance()`
- `testAdminIsAbleToDeleteMentor()`
- `testAdminIsAbleToAddNewMentor()`
- `testAdminIsUnableToAddNewMentorWhenPasswordsDoNotMatch()`
- `testReadQuestData()`
- `testParseQuestData()`
- `testUpdateQuest()`

## Test Coverage (**12%**)

| Element | Class | Method | Line |
|---------|-------|--------|------|
| com.codecool.queststore | **36%** (18/50)	| **19%** (53/269) | **12%** (242/1998) |

#### Detailed coverage
| Element | Class | Method | Line |
|---------|-------|--------|------|
| dao | **36%** (4/11) | **3%** (2/62) | **2%** (17/570) |
| databaseConnection | **50%** (1/2) | **16%** (1/6) | **8%** (2/23) |
| loginManager | **100%** (2/2)	| **100%** (7/7) | **93%** (41/44) |
| model | **55%** (5/9) | **31%** (21/66) | **42%** (57/135) |
| session | **100%** (1/1) | **28%** (2/7) | **46%** (6/13) |
| webControllers | **20%** (5/24) | **16%** (20/120) | **9%** (119/1199) |

