# Sports Evaluate - Android App

A comprehensive Android application for sports fitness evaluation using Kotlin + Jetpack Compose, Firebase Authentication, and AI integration placeholders.

## 🏗️ Architecture

- **UI**: Jetpack Compose with Material 3 Design System
- **Architecture**: MVVM (Model-View-ViewModel)
- **Navigation**: Jetpack Navigation Compose
- **Authentication**: Firebase Auth with custom email verification
- **Database**: Firebase Firestore (TODO: integration pending)
- **Image Loading**: Coil
- **AI Integration**: Placeholders for Python OCR and MoveNet

## 📱 Features

### Authentication Flow
- **Login Screen**: Email & password authentication
- **Register Screen**: Account creation with validation
- **Email Verification**: 6-digit code sent via email (1-minute expiry)
- **Code Verification Screen**: Enter and verify the received code

### Profile Setup (Multi-step)
1. **Aadhaar Details**: Name, age, gender, address, image upload
2. **Height Input**: Manual height entry in centimeters
3. **Weight Input**: Manual weight entry in kilograms
4. **Fitness Test Selection**: Choose from Push-ups, Sit-ups, Long Jump
5. **Summary Screen**: Review all details before submission

### Dashboard
- **Profile Display**: Show user details and measurements
- **Fitness Test Cards**: Interactive cards for selected tests
- **AI Analysis Section**: Placeholder for AI-generated insights
- **Logout Functionality**: Clean authentication state management

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 26+ (Android 8.0+)
- Firebase project setup

### Firebase Setup

1. **Create Firebase Project**:
   - Go to [Firebase Console](https://console.firebase.google.com)
   - Create a new project or use existing one
   - Enable Authentication with Email/Password

2. **Download Configuration**:
   - Download `google-services.json` from Firebase Console
   - Replace the placeholder file in `app/google-services.json`

3. **Update Configuration**:
   ```json
   {
     "project_info": {
       "project_number": "YOUR_ACTUAL_PROJECT_NUMBER",
       "project_id": "your-actual-project-id",
       "storage_bucket": "your-project-id.appspot.com"
     },
     // ... rest of the configuration
   }
   ```

### Email Service Configuration

**Current Implementation**: 
- For demo purposes, verification codes are logged to console
- Email sending is commented out in `EmailService.kt`

**To Enable Actual Email Sending**:
1. Uncomment email sending code in `EmailService.kt`
2. Update email credentials:
   ```kotlin
   private val senderEmail = "your-app-email@gmail.com"
   private val senderPassword = "your-app-specific-password"
   ```
3. Generate app-specific password for Gmail
4. Consider using SendGrid, AWS SES, or similar service for production

### Build and Run

```bash
# Clone the repository
git clone <repository-url>

# Open in Android Studio
# Sync project with Gradle files
# Run on device or emulator
```

## 📁 Project Structure

```
app/src/main/java/com/example/sih/
├── auth/                          # Authentication services
│   ├── AuthService.kt             # Firebase Auth wrapper
│   └── EmailService.kt            # Email verification service
├── data/
│   ├── models/                    # Data models
│   │   ├── User.kt               # User and profile models
│   │   ├── VerificationCode.kt   # Email verification model
│   │   └── ProfileSetup.kt       # Profile setup flow models
│   └── repository/               # Data repositories
│       ├── AuthRepository.kt     # Authentication data layer
│       └── UserRepository.kt     # User profile data layer
├── navigation/                   # Navigation setup
│   ├── Screen.kt                # Navigation routes
│   └── SportsEvaluateNavigation.kt # Navigation graph
├── ui/
│   ├── screens/
│   │   ├── auth/                # Authentication screens
│   │   │   ├── LoginScreen.kt
│   │   │   ├── RegisterScreen.kt
│   │   │   └── CodeVerificationScreen.kt
│   │   ├── profile/             # Profile setup screens
│   │   │   ├── AadhaarScreen.kt
│   │   │   ├── HeightScreen.kt
│   │   │   ├── WeightScreen.kt
│   │   │   ├── FitnessTestSelectionScreen.kt
│   │   │   └── SummaryScreen.kt
│   │   └── dashboard/           # Dashboard screen
│   │       └── DashboardScreen.kt
│   └── viewmodel/               # ViewModels
│       ├── AuthViewModel.kt
│       ├── ProfileSetupViewModel.kt
│       └── DashboardViewModel.kt
├── di/                          # Dependency injection
│   └── AppContainer.kt          # Manual DI container
├── MainActivity.kt              # Main activity
└── SportsEvaluateApplication.kt # Application class
```

## 🔮 AI Integration Placeholders

The app includes clear TODO comments and placeholders for future AI integration:

### Python OCR Integration
```kotlin
// TODO: Integrate Python OCR module for Aadhaar verification
// Location: EmailService.kt, AadhaarScreen.kt
// Purpose: Extract and verify Aadhaar details from uploaded images
```

### MoveNet AI Integration
```kotlin
// TODO: Integrate MoveNet AI for real-time fitness test analysis
// Location: DashboardViewModel.kt, fitness test screens
// Purpose: Real-time pose detection and exercise counting
```

### Computer Vision Features
```kotlin
// TODO: Add computer vision for exercise form detection
// Location: Dashboard and fitness test implementations
// Purpose: Analyze exercise form and provide feedback
```

## 🛠️ Technical Details

### Dependencies
- **Compose BOM**: 2024.09.00
- **Firebase BOM**: 33.6.0
- **Navigation Compose**: 2.8.4
- **Lifecycle ViewModel**: 2.9.4
- **Coil**: 2.7.0 (Image loading)
- **JavaMail**: 1.6.2 (Email service)

### Minimum Requirements
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 36
- **Compile SDK**: 36

### Material 3 Design
- Full Material 3 theming implementation
- Dynamic color support
- Consistent component usage throughout the app

## 🔧 Development Notes

### Current Limitations
1. **Email Service**: Uses placeholder implementation for demo
2. **Data Persistence**: In-memory storage (Firestore integration pending)
3. **Dependency Injection**: Manual DI (consider Dagger Hilt for production)
4. **Testing**: Unit tests need to be added
5. **Error Handling**: Basic error handling implemented

### Future Enhancements
1. **Firestore Integration**: Persistent user data storage
2. **Camera Integration**: Real-time fitness test recording
3. **AI Model Integration**: Python OCR and MoveNet models
4. **Offline Support**: Local data caching
5. **Analytics**: User behavior tracking
6. **Performance Optimization**: Image compression, lazy loading

## 📄 License

This project is created for demonstration purposes. Update license as needed for your use case.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📞 Support

For questions or issues:
1. Check existing GitHub issues
2. Create a new issue with detailed description
3. Include steps to reproduce for bugs