Add entries to this file summarising each project milestone. Don't forget that these need to have been discussed with your TA before the milestone deadline; also, remember to commit and push them by then!

# Milestone 1:

### Instructor Note from Piazza (https://piazza.com/class/ketg4v1uplv5ix?cid=162):
In case you're not really clear by the time you need to submit Milestone 1, you should submit the ideas you have so far for feedback. I also encourage you to meet with your TA and/or me to help sound out your ideas.

### Ideas for Project 2:
* Runtime analysis (memory/time)
  * Visualize runtimes of each method in a program
  * Visualize memory
  * Supply mock data for each method to make it run
  * Most likely for Java
  * Visualize using graphs and our bubbles that users can click to pull further data
  * **TA Feedback: If using a third party tool to measure memory/CPU use, need to have aspects of novelty with respect to code analysis**
   * **Can take inspiration from existing tools: https://www.blazemeter.com/**
    * **From Amir’s repo: https://github.com/amhab/StressThing**

* Run the application on different AWS images (i.e., with different cores, memories, etc.) and see the differences in terms of statistics, figure out where we start seeing diminishing return for added core. Performance to price ratio could also be calculated.

* Visualization of where a bug appears (i.e., graph of method flows -> shows a bug picture at some point)
  * **TA Feedback: Visualize bugs as they relate to the source code/commits**
   * **Dependencies, location in source code**
  * **Could constrain the domain to a particular language and bug type**
* UML diagram generator
  * **TA Feedback: Missing novelty aspect required for project, needs more creativity**
  * **Popular idea, may be difficult to make a unique project**
* Detect coupling within code projects
  * Binding to concrete classes vs to interfaces
  * **TA Feedback: Interesting idea, but vague and possibly difficult to implement**

# Milestone 2:

### Planned division of main responsibilities between team members

To be assigned later based on how we decide to carry out the project (i.e., more focus on visualization or more focus on analysis)
* Solidify ideas for concrete visual representation 
* Research tools to be used for analysis
* Team generating visual components
* Team converting data from the code to something usable
* Team analysing the code and pulling data from it

### Roadmap for what should be done when

* Nov 7 Choose the project and solidify ideas
* Nov 10 Have drawings for basic idea of the visualized product
* Nov 11 Have rough idea of project structure and methods we want to make 
* Nov 12 Run a initial user test based on our rough project idea and gain feedback
* Nov 13 Team meeting for milestone 3 and user test review
* Nov 14 Redraw of project and fix holes based on user feedback
* Nov 15 Have all the methods and project structure layed out
* Nov 16 Start implementing 
* Nov 20-25 Finish implementation
* Nov 26 Final user study
* Nov 27 Make updates to visualization and code based on user feedback
* Nov 30 9am Due date

### Summary of progress so far

Discussed project timeline and solidified the ideas for the project.
Examples of feedbacks/changes/conclusion:

- Abandoned AWS idea due to complexity
- Focus more on static analysis
- Examples of static analysis: class interdependency, method interdependency, bug warning (i.e., where an exception "could" be triggered), infinite loop detection (i.e., while(true){..}), inefficiency warning (i.e., identify some inefficient patterns or inefficient usage of data structures)
- Examples of dynamic analysis: method flow, infinite loops (i.e., where it actually occurs), bug (i.e., where it actually gets triggered)
- Either have complex analysis + less focus on visualization, or shallower analysis + unique visualization. Need to balance out.
- Try to implement as much features as you can to deepen the level of analysis
- Great examples/ideas for static analysis: https://codenarc.org/codenarc-rule-index.html

Based on multiple TA feedbacks, we have decided to focus the project on:
**"Analyzing a project and visualizing warnings for potential issues"**
We will:
- focus mainly on static analysis class [i.e., class interdependency, method interdependency, bug warning (i.e., where an exception "could" be triggered), infinite loop detection (i.e., while(true){..}), inefficiency warning (i.e., identify some inefficient patterns or inefficient usage of data structures), empty method]
- add a unique visualization (i.e., filtering option to toggle on/off visualizing bug warning for example)
- refer to (https://codenarc.org/codenarc-rule-index.html) for more ideas on static analysis

# Milestone 3:

### Static analysis ideas:
* Detecting infinite loops
* Class/method interdependency
* Bug warning (i.e., where an exception "could" be triggered), infinite loop detection (i.e., while(true){..})
* Inefficiency warning (i.e., identify some inefficient patterns or inefficient usage of data structures)
* Empty method

### Visualization:
* Red represents possible sources of errors to do with loops
* Yellow for empty methods
* Green for methods without possible loop problems
* Lines to represent dependencies 
* Blue for non fatal errors but possible bugs such as array analysis on empty arrays
* Checks for unused variables
* Mockup example: ![Example](https://github.students.cs.ubc.ca/cpsc410-2020w-t1/cpsc410_project2_team18/blob/master/pasted%20image%200.png)

### User feedback:
* What is the red box?
* Documentation for the boxes needed
* Display variable names
* Confusion over the boxes can you click and zoom into the classes
* He recognizes this tool similar to security analysis (Microsoft threat monitoring tool)
* USER WANTS: threading errors (not going to add)

# Milestone 4:

### Status:
* Solidified idea (look for potential issues with for and while loops, empty method/code blocks)
  * **TA feedback: can pick from a list of rules here https://rules.sonarsource.com/java/ related to different concepts (loops especially in our case) then provide our own implementation**
* Project structure completed
* Implementation to start this weekend
  * **TA feedback: our group is a bit behind schedule**
* Split up tasks between members
  * Hee Su to focus on visualization
  * David to pull the AST out
  * Rest of us to work on program analysis
* Need to decide on a visualization tool
  * **TA feedback: can use https://d3js.org/ as a tool for visualization, although it is in JavaScript**
* **TA feedback: if we want to include dynamic analysis, we can use Java Reflection API which is built-in**

### Plans for final user study:
  * Similar to the first one, we will give user instructions on how to set up and run our program and get feedback on the process of setting the program up and getting the resulting visual output. 

### Timeline for remaining days:
  * Nov 20-23 Finish initial implementation
  * Nov 25-26 Final user study
  * Nov 27-28 Update the implementation based on user feedback
  * Nov 28-29 Create the video


