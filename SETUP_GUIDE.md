# 🚀 Sports Evaluate - Complete Setup Guide

## 📋 Prerequisites
- Android Studio Arctic Fox or later installed
- Google account for Firebase
- Android device or emulator (Android 8.0+ / API 26+)

## 🔥 Firebase Setup (Detailed Steps)

### Step 1: Create Firebase Project

1. **Open Firebase Console**
   - Go to [https://console.firebase.google.com](https://console.firebase.google.com)
   - Sign in with your Google account

2. **Create New Project**
   - Click "Create a project" or "Add project"
   - Enter project name: `sports-evaluate` (or your preferred name)
   - Enable Google Analytics (recommended) - Click "Continue"
   - Choose Analytics account or create new one - Click "Create project"
   - Wait for project creation to complete - Click "Continue"

### Step 2: Add Android App to Firebase

1. **Add Android App**
   - In Firebase Console, click "Add app" or the Android icon
   - **Android package name**: `com.example.sih` ⚠️ **IMPORTANT: Use exactly this**
   - **App nickname**: "Sports Evaluate" (optional)
   - **Debug signing certificate SHA-1**: Leave blank for now (optional)
   - Click "Register app"

### Step 3: Download Configuration File

1. **Download google-services.json**
   - Click "Download google-services.json"
   - Save the file to your computer

2. **Replace in Project**
   - In Android Studio, navigate to: `app/` folder
   - Delete the existing `google-services.json` file
   - Copy your downloaded `google-services.json` file into the `app/` folder
   - The file should be at: `app/google-services.json`

### Step 4: Enable Authentication

1. **Go to Authentication**
   - In Firebase Console, click "Authentication" in left sidebar
   - Click "Get started" if it's your first time

2. **Enable Email/Password**
   - Click "Sign-in method" tab
   - Find "Email/Password" in the list
   - Click on it
   - Toggle "Enable" to ON
   - Click "Save"

3. **Optional: Configure Settings**
   - You can customize email templates later
   - For now, defaults are fine

## 🔧 Android Studio Setup

### Step 1: Open Project
```bash
1. Open Android Studio
2. Click "Open an Existing Project"
3. Navigate to: C:\Users\purus\AndroidStudioProjects\sih
4. Click "OK"
```

### Step 2: Sync Project
```bash
1. Wait for Android Studio to load the project
2. You'll see a "Sync Now" banner - click it
   OR
3. Go to File → Sync Project with Gradle Files
4. Wait for sync to complete (may take 1-2 minutes)
```

### Step 3: Verify Firebase Integration
After sync, check for any errors in the "Build" tab at the bottom of Android Studio.

## 📱 Running the App

### Option 1: Physical Device
1. **Enable Developer Options**
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
   - Go back to Settings → Developer Options
   - Enable "USB Debugging"

2. **Connect Device**
   - Connect phone via USB cable
   - Allow USB debugging when prompted
   - Device should appear in Android Studio toolbar

3. **Run App**
   - Click green "Run" button (▶️) in toolbar
   - Or press `Shift + F10`
   - Select your device
   - Wait for app to install and launch

### Option 2: Android Emulator
1. **Create Emulator**
   - Click "Device Manager" tab (right side of Android Studio)
   - Click "Create Device"
   - Choose "Phone" → "Pixel 4" (or similar)
   - Select API 26 or higher (Android 8.0+)
   - Click "Next" → "Finish"

2. **Run Emulator**
   - Click "Run" button next to your emulator
   - Wait for emulator to boot (2-3 minutes first time)

3. **Run App**
   - Click green "Run" button in toolbar
   - Select your emulator
   - Wait for app to install and launch

## 🧪 Testing the App

### Test Authentication Flow
1. **Register New User**
   - Open app → Click "Don't have an account? Register"
   - Enter email: `test@example.com`
   - Enter password: `password123`
   - Confirm password: `password123`
   - Click "Register"

2. **Check Verification Code**
   - Look at Android Studio's "Logcat" tab (bottom of screen)
   - Search for "EmailService" or "Verification code"
   - You'll see: `Verification code for test@example.com: 123456`
   - Enter this 6-digit code in the app

3. **Complete Profile Setup**
   - Fill in Aadhaar details (use dummy data)
   - Upload any image from gallery
   - Enter height: `175`
   - Enter weight: `70`
   - Select fitness tests
   - Review and submit

4. **Access Dashboard**
   - View your profile information
   - See fitness test cards
   - Check AI analysis placeholder

## 🔍 Troubleshooting

### Common Issues & Solutions

#### 1. **"Default FirebaseApp is not initialized"**
```
Solution:
- Ensure google-services.json is in app/ folder
- Sync project again
- Clean and rebuild: Build → Clean Project → Rebuild Project
```

#### 2. **"Package name mismatch"**
```
Solution:
- Verify package name in Firebase is: com.example.sih
- Check app/build.gradle.kts has: applicationId = "com.example.sih"
```

#### 3. **"Google Services plugin not found"**
```
Solution:
- Ensure internet connection during sync
- Try: File → Invalidate Caches and Restart
```

#### 4. **Email verification not working**
```
Current Status: Codes are logged to console for demo
To enable real emails:
1. Uncomment email code in EmailService.kt
2. Add Gmail app password
3. Update sender credentials
```

#### 5. **Build errors**
```
Solution:
- Check minimum SDK is 26+
- Ensure all dependencies are downloaded
- Try: Build → Clean Project
```

## 📊 Firebase Console Monitoring

### View Users
1. Go to Firebase Console → Authentication → Users
2. See registered users and their verification status

### View Logs
1. Go to Firebase Console → Functions → Logs (if using Cloud Functions)
2. Monitor authentication events

### Analytics (if enabled)
1. Go to Firebase Console → Analytics
2. View app usage statistics

## 🔐 Security Notes

### For Development
- The current setup is for development/testing
- Email service uses console logging for demo

### For Production
- Replace demo email service with proper email provider
- Add proper error handling and validation
- Enable Firebase Security Rules
- Add ProGuard/R8 obfuscation
- Use encrypted storage for sensitive data

## 📱 Expected App Flow

```
1. Login Screen (Email + Password)
   ↓
2. Register Screen (if new user)
   ↓
3. Email Verification (6-digit code)
   ↓
4. Profile Setup:
   - Aadhaar Details (Name, Age, Gender, Address, Image)
   - Height Input
   - Weight Input
   - Fitness Test Selection
   - Summary Review
   ↓
5. Dashboard (Profile + Fitness Tests + AI Placeholders)
```

## 🎯 Package Name Information

**Important**: The app is configured with package name `com.example.sih`

- **Firebase Console**: Use `com.example.sih`
- **App ID**: `com.example.sih` 
- **Application ID**: Already set in `app/build.gradle.kts`

## 🚀 Ready to Go!

After completing these steps, your Sports Evaluate app will be:
- ✅ Connected to Firebase
- ✅ Authentication enabled
- ✅ Profile setup functional
- ✅ Dashboard accessible
- ✅ Ready for AI integration

The app includes comprehensive TODO comments for future Python OCR and MoveNet AI integration!