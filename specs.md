# Project Specification

# Multi-Threaded Client-Server Application for Timetable Scheduling

This project extends the functionality of a previously developed client-server application to support multi-threaded operations, allowing multiple clients to schedule a timetable concurrently. The server application will handle each client connection in a separate thread, ensuring synchronization and controlled access to a shared memory-based data collection.

## Description.

The application now supports multiple clients scheduling a timetable in parallel or concurrently. Each client connection to the server initiates a new thread. These server-side threads are responsible for:

1. Receiving requests/messages sent by the client application.
2. Calling the appropriate method to perform the indicated action.
3. Replying to the client with information based on the performed action.

The server must ensure synchronization and control access to shared data (e.g., `ArrayList`, `HashMap` objects). Clients requesting ‘display’ will receive all timetable information, which they will display in a proper GUI.

Additionally, a new feature allows clients to request ‘early lectures’, prompting the server to reschedule classes to earlier morning times if possible. This feature utilizes a Fork-Join rule with a divide-and-conquer algorithm, assigning different threads to shift classes for each day of the week. The server decouples the JavaFX part from the ‘early lectures’ feature using the `Task` and `Worker` phenomena from the `javafx.concurrent` package, allowing it to handle other requests concurrently.

## Assessment Criteria

- **~~Implementation of Multi-Threading for Multi-Clients:~~** 10%
- **‘Early Lectures’ Test – With All Events and Divide and Conquer**: 20%
- **Implementation of Fork-Join Rule using Divide and Conquer**: 20%
- **~~Implementation of Synchronization and Controlled Access:~~** 20%
- **Display Timetable on Client-Side Using Proper GUI**: 10%
- **~~Implementation of `javafx.concurrent`~~**: 10%
- **~~Code Structure and Modularization – Implementation of MVC~~**: 10%
- **BONUS: Any Additional Convincing Feature**: Up to 40%

## Submission Format

Name the applications with your student IDs. For example, if two group members have the IDs 21237333 and 21222344, name the applications as follows:

- `21237333_21222344_Server`
- `21237333_21222344_Client`
- `21237333_21222344_video`

Zip both applications and video files into a folder named `21237333_21222344.zip`. Submit this zip file on Brightspace.

## Important Notes

- **Plagiarism**: If found, both projects will receive zero marks, and the case will be forwarded to the disciplinary committee. It is your responsibility not to copy or disclose your project.
- **Client-Server Communication**: Managed by a thread for each connection.
- **Shared Resource Management**: Implement a mechanism for thread synchronization.
- **Performance**: The task of ‘early lectures’ must be decoupled and performant.
- **Testing**: Launch multiple executions of the Client application to simulate concurrent exchanges with the server.