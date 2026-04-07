# advjava_project
# Simple Event Management System

A beginner-friendly Java Swing project for managing event records with a desktop GUI.

## Overview

This project is a small event management application built using only Java.  
It lets the user add, update, delete, and view events in a table through a simple graphical interface.

The project was intentionally kept simple so it is easy to understand for beginners learning:

- Java basics
- object-oriented programming
- Java Swing GUI development
- event handling
- table display using `JTable`

## Features

- Add a new event
- Update a selected event
- Delete a selected event
- View all events in a table
- Load sample events when the app starts
- Show the total number of events
- Basic input validation for empty fields and attendee count

## Event Fields

Each event contains:

- Title
- Date
- Venue
- Organizer
- Type
- Number of Attendees

## Technologies Used

- Java
- Java Swing

No external libraries or frameworks are used.

## Project Structure

The project is intentionally very small:

```text
java_proj/
├── App.java
├── run.sh
├── README.md
└── .gitignore
```

## Main Files

### `App.java`

This is the complete application source file. It contains:

- the main method
- the Swing GUI
- the event form
- the event table
- add, update, delete, and clear actions
- a simple inner `Event` class

### `run.sh`

This shell script:

- compiles `App.java`
- creates the `out/` folder automatically
- runs the program
- clears some Snap/GTK environment variables so Swing works better in VS Code Snap terminals

### `.gitignore`

This ignores:

- `out/` generated class files

## How to Run

### Option 1: Run using the script

```bash
chmod +x run.sh
./run.sh
```

### Option 2: Compile and run manually

```bash
javac -d out App.java
java -cp out App
```

## Requirements

- Java JDK 17 or later recommended
- Linux, Windows, or macOS with a graphical desktop environment

You can check Java using:

```bash
java -version
javac -version
```

## How the Application Works

When the application starts:

- a window opens with a simple event management GUI
- sample events are loaded into the table
- the total event count is shown at the top

The window has two main sections:

### Left side: Event form

The form allows the user to enter:

- title
- date
- venue
- organizer
- event type
- attendee count

Buttons:

- `Add` adds a new event
- `Update` updates the currently selected event
- `Clear` clears the form fields

### Right side: Event table

The table shows all stored events.

When the user clicks a row:

- the selected event data fills the form
- the user can edit it and press `Update`

The `Delete` button removes the selected event.

## Validation Included

The program checks:

- all fields must be filled
- attendees must be a number
- attendees cannot be negative
- a row must be selected before update or delete

## Sample Data

The app starts with a few sample events such as:

- Tech Conference
- Java Workshop
- Music Party

These are hardcoded inside the program to make testing easier.

## Current Limitations

This is a beginner project, so it keeps things simple:

- data is stored only in memory
- events are lost after closing the application
- dates are stored as plain text
- there is no database
- there is no login system
- there is no search or filter feature

## Possible Future Improvements

- save data to a file
- use a database like MySQL
- add date picker support
- add search and filter options
- improve form layout
- add separate Java classes for better structure
- export events to CSV

## Learning Value

This project is useful for practicing:

- classes and objects
- arrays or lists
- Swing components
- action listeners
- layout managers
- table models
- form validation

## Author

GitHub: `AliveCore729`
