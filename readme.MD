# Project Overview

This project is a server-client application designed to manage and interact with a class schedule system. It enables clients to add or remove classes from their schedule, view their current schedule, and terminate the connection to the server. The server, in response, can inform the client when the timetable is full or confirm that a class has been successfully added. This simple yet efficient communication protocol streamlines the process of schedule management for both parties.

## Comments
This is just the core code from part 1 of the project. This will be adapted for part 2 of the project.

## Actions Clients Can Request

- `ac` -> **Add Class**: Adds a specified class to the schedule.
- `rc` -> **Remove Class**: Removes a specified class from the schedule.
- `ds` -> **Display Schedule**: Displays the current class schedule.
- `st` -> **Stop (Close Connection)**: Terminates the connection with the server.

## Server Responses

### Add Class
- `ol` -> **Class overlaps**
- `im` -> **Invalid Module**
- `ir` -> **Invaild Room**
- `ca` -> **Class added**
- `ttf` -> **TimeTable full**

### Remove class
- `nsc` -> **No scheduled class**
- `cr + details` -> **Class removed + details about**
- `cnf` -> **No class found**

### Display Schedule
- `cp` -> **Class printed**
- `cnf` -> **No class found**

### Stop
- `TERMINATE` -> **Terminate**


### Client to Server Message Format

1. **Add Class (`ac`)**: Requires additional parameters.
    - Format: `ac <ModuleCode> <StartTime-EndTime> <Day> <Room>`
    - Example: `ac CS4076 09:00-10:00 monday CS-4005B`
2. **Remove Class (`rc`)**: Requires additional parameters.
    - Format: `rc <ModuleCode> <StartTime-EndTime> <Day> <Room>`
    - Example: `rm CS4076 09:00-10:00 monday CS-4005B`
3. **Display Schedule (`ds`)**: Requires the module code for schedule.
    - Format: `ds <ModuleCode>`
    - Example: `ds CS4076`
5. **Terminate Connection (`st`)**: No additional parameters required.


### Detailed Explanation

- For the `ac` action, clients must specify the module code, time, day, and room where the class is to be added. This detailed information is essential for scheduling the class correctly.
- For the `rc` action, clients must specify the module code, time, day, and room where the class is to be added. This detailed information is essential for removing the correct class.
- For the `ds` action, clients must specify the module code
- `st` (stop) do not require additional information beyond the initial action command.

This structured approach to message formatting ensures a straightforward and effective interaction between the client and server, facilitating easy schedule management.
 
### Specification
The assignment specification can be found in specs.md