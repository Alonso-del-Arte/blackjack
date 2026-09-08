This project will be open to Hacktoberfest 2026.

# Guidelines for Hacktoberfest 2026 Contributors

Preferably you are someone who has played blackjack, though not necessarily in a 
real casino with real money.

## Guidelines for "low-code" or "no-code" track contributors

I need help with internationalization (i18n). If you know the terminology for 
blackjack specifically or playing cards generally in a language other than 
English, I could use your help. See the src/i18n folder.

I also need help from experienced blackjack players with [the game rules 
document](GameRules.md) and [the game variants document](Variants.md), verifying 
that they are as correct as we can make them. If you have played a blackjack 
variant, I'd appreciate your proofreading the existing section on that variant, 
or starting a section on that variant if it doesn't already exist.

## Guidelines for programming track contributors

I will definitely consider pull requests associated with an issue and maybe 
consider pull requests associated with a TODO (considered an Action Item in 
NetBeans).

Issues about build tools will be deleted. It's one thing for me to deal with 
Maven headaches at my day job, I don't want to deal with Maven headaches on my 
free time. Just use whatever build tool your IDE uses by default.

Contributors should have a proper Java IDE (most are available for free) and 
JUnit. This project started out with Java 8 and JUnit 4. But so as to avail 
myself to certain advances in later Java versions, and to simplify 
internationalization, I have upgraded the project to Java 21. However, I 
continue to use JUnit 4 for testing.

Contributors may use later versions of Java provided they don't use features not 
available in Java 21. Using JUnit 4 should be no problem in an IDE that also has 
JUnit 5 (e.g., IntelliJ IDEA).

Also note that the principal branch of this repository is named "main" but it 
was not originally so. If you cloned or forked prior to the branch rename, 
please update your clone or fork before making any pull requests.

* Column width is 80.
* Prefer spaces to tabs (this is likely to be an issue only with Eclipse, a 
simple configuration change should take care of it).
* Opening curly braces should not go on lines by themselves.
* As much as is practical, each class in Source Packages should have a 
corresponding test class in Test Packages. That includes abstract classes as 
well as interfaces having one or more default implementations.
* Do not delete test classes nor test stubs without explanation.
* Do not add dependencies, except Hamcrest and JUnit if your IDE does not have 
those in the project at the outset.
* No comments except Javadoc, TODO or FIXME in the overall pull request. 
However, intermediate commits may contain other kinds of comments provided they 
are removed in a later commit in the pull request.
* Prefer small commits. But avoid making a pull request for a single commit 
(unless it's to fix a defect affecting end users).
* Pull requests should address a TODO or FIXME comment in the source, or an open 
issue on GitHub.
* Do not knowingly violate anyone's copyright, patents or trademarks. Be aware 
that some blackjack variants, such as Blackjack Switch and Spanish 21, are 
modern intellectual property.
