# Ledger

## Proposal

**Ledger** is a Java desktop application that offers users with simple and smart ways to keep track of income and 
expense items. The application includes some basic features that allow users to edit and review a list of budget items 
with related information, such as date, category, payer or payee, description and amount, anywhere and anytime with 
their laptop. The application also includes other features that helps users to become financial well-being. Users can 
generate a summary of their savings and spending, such as the percentage of expenses under different categories and the 
changes of financial condition over time to better plan their budget. Users can search and filter specific items with 
certain criteria as well. Anyone who wants to be well-organized and well-planned financially will use the application. 
Students, professionals, housewives, etc. might find it useful.

This project is of interest to me because financial data is of great importance for individuals and companies. People 
can live a better life and companies can become more successful if they plan better in finance. I have studied 
accounting for a few years and have knowledge and understanding of financial concepts.

## User Stories

- As a user, I want to be able to *add* an income or expense item to my ledger.
- As a user, I want to be able to *delete* an income or expense item to my ledger.
- As a user, I want to be able to *review* a detailed list of items on my ledger.
- As a user, I want to be able to *generate* a financial summary from my ledger.
- As a user, I want to be able to *save* my ledger to file.
- As a user, I want to be able to *load* my ledger from file when the program starts.

## Instructions for Grader

- You can generate the first required event by filling in the six fields labelled "Type", "Date", "Payee/Payer Name", 
"Description", "Category" and "Amount" in the input pane then clicking the button labelled "Add Item". You can click
the "Review Details" button to view the list.

- You can generate the second required event by selecting an item from the display pane then clicking the button 
labelled "Delete Item". Note that the deletion event is only allowed when the list is displayed while the delete button
is disabled after you click "Hide Details".

- You can locate my visual component by clicking the button labelled "Generate Report". The image changes based on the 
net income in the current ledger.

- You can save the state of my application by clicking the "Save Ledger" button. In case you forget to save the data 
before exiting the program, a recover.txt file is saved when you close the window.

- You can reload the state of my application by clicking the "Load Ledger" button then selecting the txt file you would
like to reload.

- More details can be found in GUI Navigation section below.

## GUI Navigation

- The control pane at the bottom of the main window has six buttons for six user stories. 
- The user can either load data from an existing file by clicking the Load Ledger button to open a file chooser where 
they can select a txt file, or start with an empty ledger. In this demo, you may select the myLedger.txt file from the 
data folder. 
- The input pane on the left of the main window has six input fields to add an item. The category list combo box changes 
according to the type radio button selected. The amount formatted text field helps format the amount entered by the user
to a positive number with two decimal points. The user can click the Add Item button after filling in the fields. If the
user try to add a duplicate item, a message will pop up. In this demo, you may try to add different items, then just 
click the add button twice to see the duplicate item message.
- The display pane on the right of the main window has a scroll pane to display the item list. The user can click the
Review Details button to view the ledger. After the item list is displayed, the Review Details button turns into Hide
Details button so the user can hide items if confidential. When the display is on, the item lists will be updated in 
real-time if an item is added or deleted. In this demo, you may toggle between review and hide, and try to add a new 
item and delete an item from the list to see the view updates.
- The user can delete an item by selecting that item on the display pane. The Delete Item button is disabled when 
details are hidden to avoid deleting an item accidentally. If the user click the delete button the ledger is empty or 
without selecting an item, a message will pop up. In this demo, you may first turn on the review details then click 
delete to see the empty selection message, then select an item from the list to check the actual delete, then finally 
deleting all the items and click delete one more time to see the empty ledger message.
- The user can generate summary by clicking the Generate Report button then a new window will open. In addition to the 
financial summary, maximum single items and category percentage analysis on the left, there will be a picture on the 
right showing your current financial status. In this demo, you can enter different combinations of items to check the 
calculation and try net income >= 1000000, <= 0 or in between to view different interesting pictures.
- The user can finally save data by clicking the Save Ledger button. However, even if the user forget to save before
exit the application, a recover.txt file will be created in the data folder. In this demo, you may try to save the 
ledger, or just simply close the window then check the recover.txt file.