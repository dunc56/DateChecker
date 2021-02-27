Date check code. 
I added the code in one commit - then realised I should have done it piecemeal to show the creation of tests and then the code to make the tests pass. 

Things to consider if given more time :-
1. Locale - will the call centre be in a different locale ? 
2. use an Immutable map for the storgae of the opening times ? 
3. refer the tests to the constants for times rather than hard coding.

download the repo - unzip it. 

Then just run :-
mvn test 
in the root folder and all 10 tests will pass. 
