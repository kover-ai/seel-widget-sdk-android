#!/bin/bash

# Seel Widget SDK Distribution Build Script

echo "🚀 Starting Seel Widget SDK distribution build..."

# Clean previous builds
echo "🧹 Cleaning previous builds..."
./gradlew clean

# Build release version
echo "🔨 Building release version..."
./gradlew :widget:assembleRelease

# Copy AAR file to distribution directory
echo "📦 Copying AAR file..."
./gradlew :widget:copyAar

# Create distribution package
echo "📁 Creating distribution package..."
DIST_DIR="seel-widget-sdk-distribution"
rm -rf $DIST_DIR
mkdir -p $DIST_DIR

# Copy files
cp dist/seel-widget-sdk-1.0.0.aar $DIST_DIR/
cp MANUAL_INTEGRATION_GUIDE.md $DIST_DIR/
cp MANUAL_INTEGRATION_GUIDE_EN.md $DIST_DIR/
cp -r example-manual-integration $DIST_DIR/

# Create distribution README
cat > $DIST_DIR/README.md << EOF
# Seel Widget SDK Distribution Package

## 📦 Included Files

- \`seel-widget-sdk-1.0.0.aar\` - SDK AAR file
- \`MANUAL_INTEGRATION_GUIDE_EN.md\` - Manual integration guide (English)
- \`MANUAL_INTEGRATION_GUIDE.md\` - Manual integration guide (Chinese)
- \`example-manual-integration/\` - Complete example project

## 🚀 Quick Start

1. Copy \`seel-widget-sdk-1.0.0.aar\` to your project's \`libs/\` directory
2. Add dependency in \`build.gradle\`:
   \`\`\`gradle
   dependencies {
       implementation files('libs/seel-widget-sdk-1.0.0.aar')
       // Add required dependencies...
   }
   \`\`\`
3. Follow \`MANUAL_INTEGRATION_GUIDE_EN.md\` for integration
4. Check \`example-manual-integration/\` example project

## 📞 Technical Support

- Documentation: MANUAL_INTEGRATION_GUIDE_EN.md
- Example: example-manual-integration/
- Support: support@seel.com
EOF

# Create archives
echo "📦 Creating archives..."
tar -czf seel-widget-sdk-1.0.0.tar.gz $DIST_DIR/
zip -r seel-widget-sdk-1.0.0.zip $DIST_DIR/

echo ""
echo "✅ Build completed!"
echo ""
echo "📁 Generated files:"
echo "  - AAR file: dist/seel-widget-sdk-1.0.0.aar"
echo "  - Distribution directory: $DIST_DIR/"
echo "  - Archive: seel-widget-sdk-1.0.0.tar.gz"
echo "  - Archive: seel-widget-sdk-1.0.0.zip"
echo ""
echo "📋 Usage:"
echo "1. Copy AAR file to target project's libs/ directory"
echo "2. Add dependency in build.gradle"
echo "3. Follow integration guide for configuration"
echo "4. Check example project for specific usage"
echo ""
echo "📖 Detailed instructions: $DIST_DIR/README.md"
