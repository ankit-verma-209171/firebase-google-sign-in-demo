import { OAuth2Client } from 'google-auth-library';
import express from 'express';
import jwt from 'jsonwebtoken';

const app = express();
app.use(express.json());

const CLIENT_ID = Bun.env.WEB_CLIENT_ID;
const JWT_SECRET = Bun.env.JWT_SECRET || 'your-secret'; // Set this in your .env

if (!CLIENT_ID) {
    throw new Error('WEB_CLIENT_ID is not set in environment variables');
}

const client = new OAuth2Client(CLIENT_ID);

interface GoogleLoginRequest {
    idToken: string;
}

interface AuthResponse {
    email: string;
    accessToken: string;
}

interface ErrorResponse {
    error: string;
}

function createYourJwt(payload: { email: string }) {
    // Use a real JWT library for secure tokens
    return jwt.sign(payload, JWT_SECRET, { expiresIn: '1h' });
}

app.post(
    '/api/auth/google',
    async (req: express.Request<{}, {}, GoogleLoginRequest>, res: express.Response<AuthResponse | ErrorResponse>) => {
        const { idToken } = req.body;
        console.log('Received idToken:', idToken);
        console.log('Client ID:', CLIENT_ID);

        if (!idToken) {
            return res.status(400).json({ error: 'idToken is required' });
        }

        try {
            const ticket = await client.verifyIdToken({
                idToken,
                audience: CLIENT_ID,
            });

            console.log('Token verified successfully');
            const payload = ticket.getPayload();
            const userEmail = payload?.email;

            console.log('User email from token:', userEmail);
            if (!userEmail) {
                return res.status(400).json({ error: 'Invalid token payload' });
            }

            // Optional: Check domain, create user if new, etc.

            const jwtToken = createYourJwt({ email: userEmail });
            res.json({
                accessToken: jwtToken,
                email: userEmail
            });
        } catch (error) {
            res.status(401).json({ error: 'Invalid Google ID token' });
        }
    }
);

app.listen(3000, () => {
    console.log('Server is running on port 3000');
});