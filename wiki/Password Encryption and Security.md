To ensure maximum security, we have used different encryption and hash technologies to store and transfer passwords of the users. We never transfer or store plaintext passwords within our system.

## Frontend Client

When a user tries to register or login using the frontend client, the password gets hashed through SHA-256 and then sent to the backend.

## Backend Client

When the backend receives a request with a hashed password, it encodes the hashed password with BCrypt and then stores it.
