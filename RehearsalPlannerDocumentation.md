# RehearsalPlanner Documentation

## Overview
RehearsalPlanner is a console-based application designed to streamline the creation of weekly rehearsal schedules for actors or artists. It efficiently manages actors' availability and automatically generates optimized schedules, eliminating the need for manual scheduling.

## Core Components

### 1. Person Class
Base class representing an individual in the system.
- **Key Features:**
  - Auto-generated staff ID system
  - Basic personal information management (name, email, phone)
  - Unique identifier tracking through static `nextStaffId`

### 2. Actor Class (extends Person)
Manages actor-specific functionality, particularly availability scheduling.
- **Key Features:**
  - Weekly availability tracking using boolean arrays
  - Four daily time slots (9-12, 12-15, 15-18, 18-21)
  - Color-coded availability display (Green: Available, Red: Unavailable)
  - Day-wise availability management

### 3. Schedule Class
Core scheduling engine that manages actor schedules and generates timetables.
- **Key Features:**
  - Actor roster management
  - Schedule generation based on availability
  - Color-coded schedule output:
    - Green: 3+ actors available
    - Yellow: 2 actors available
    - Red: 1 actor available
    - White: No actors available

### 4. LoadSave Class
Handles data persistence operations.
- **Key Features:**
  - Saves actor data and availability to file
  - Loads saved schedules
  - File format: `RehearsalPlanner.txt`
  - Robust error handling for I/O operations

### 5. AppNavigation Class
Main user interface controller providing menu-driven interaction.
- **Key Features:**
  - Interactive menu system
  - Actor management operations
  - Availability setting interface
  - Schedule generation and viewing
  - Data persistence operations

## Key Functions

### Actor Management
- Add new actors with personal details
- Remove existing actors
- View actor information
- Set and modify actor availability

### Schedule Management
- Generate weekly schedules
- View color-coded availability
- Save and load schedules
- Time slot management (4 slots per day)

### Data Operations
- Persistent storage of actor data
- File-based data management
- Error handling for data operations

## Technical Notes
- Console-based interface with color coding
- Data persistence in text file format
- Input validation for all user interactions
- Modular design with clear separation of concerns
- Extensible architecture for future enhancements

## Future Enhancements
- Scheduling conflict detection (planned)
- GUI implementation (planned)
- Extended scheduling options
- Multiple schedule management

## Usage Guidelines
1. Add actors through the main menu
2. Set availability for each actor
3. Generate schedule to view optimal rehearsal times
4. Save data for future reference
5. Load previous schedules as needed
