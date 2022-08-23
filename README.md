# My Personal Project

## To-do list

The **to-do list** application will be able to: 
- allow the user to add and remove tasks
- show all items

This application can be used by anyone to keep track of their agenda. I have
decided to make a **to-do list** application since it will be useful in the future to keep track of assignments or
important tasks. As someone who doesn't usually use other forms of agenda to keep track of things, I hope that by 
making this application, it will give me an incentive to use it.

## User Stories

- As a user, I want to be able to add a task to my to-do list
- As a user, I want to be able to view the list of tasks on my to-do list
- As a user, I want to be able to mark a task as complete on my to-do list
- As a user, I want to be able to delete a task from my to-do list
- As a user, I want to be able to save my to-do list to file
- As a user, I want to be able to be able to load my to-do list from file 

## Phase 4: Task 2
Thu Nov 25 21:35:06 PST 2021
Added Task 'brush teeth' to to-do list.
- When a task is added to the to-do list.

Thu Nov 25 21:35:13 PST 2021
Completed Task 'brush teeth' in to-do list.
- When a task in the to-do list is completed.

Thu Nov 25 21:35:14 PST 2021
Removed Task 'brush teeth' from to-do list.
- When a task is removed from the to-do list.

## Phase 4: Task 3
- My MainFrame class which extends JFrame is composed of several panels that make up the gui. I think I would've
refactored each panel into their own classes to allow for better readability.
- Much of the methods in my MainFrame class are difficult to read, and it would be beneficial to extract parts of the
code into new methods that describe the behavior of the code.
- I found that there are methods in MainFrame and Toolbar that are very similar and essentially performs the same 
function, but differs very slightly. If given more time I would refactor them into a single method to avoid duplicate
code.