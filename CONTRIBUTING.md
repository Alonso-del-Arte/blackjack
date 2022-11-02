This project will be open to Hacktoberfest 2023.

# Guidelines for Hacktoberfest 2023 Contributors

I will definitely consider pull requests associated with an issue and maybe 
consider pull requests associated with a TODO (considered an Action Item in 
NetBeans).

Issues about build tools will be deleted. It's one thing for me to deal with 
Maven headaches at my day job, I don't want to deal with Maven headaches on my 
free time. Just use whatever build tool your IDE uses by default.

Contributors should have a proper Java IDE (most are available for free) and 
JUnit. This project uses Java 8 and JUnit 4. Contributors may use later versions 
of Java provided they don't use features not available in Java 8. Using JUnit 4 
should be no problem in an IDE that also has JUnit 5 (e.g., IntelliJ IDEA).

Also note that the principal branch of this repository is named "main" but it 
was not originally so. If you cloned or forked prior to the branch rename, 
please update your clone or fork before making any pull requests.

* Column width is 80.
* Prefer spaces to tabs.
* Opening curly braces should not go on lines by themselves.
* As much as is practical, each class in Source Packages should have a 
corresponding test class in Test Packages.
* Do not delete test classes nor test stubs without explanation.
* Do not add dependencies.
* No comments except Javadoc, TODO or FIXME.
* Prefer small commits. But avoid making a pull request for a single commit 
 (unless it's to fix a defect).
* Pull requests should address a TODO or FIXME comment in the source, or an open 
 issue on GitHub.
