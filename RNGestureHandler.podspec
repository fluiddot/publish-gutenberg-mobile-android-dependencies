require "json"

fabric_enabled = ENV['RCT_NEW_ARCH_ENABLED']

Pod::Spec.new do |s|
  # NPM package specification
  package = JSON.parse(File.read(File.join(File.dirname(__FILE__), "package.json")))

  s.name         = "RNGestureHandler"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = "https://github.com/software-mansion/react-native-gesture-handler"
  s.license      = "MIT"
  s.author       = { package["author"]["name"] => package["author"]["email"] }
  s.source       = { :git => "https://github.com/software-mansion/react-native-gesture-handler", :tag => "#{s.version}" }
  s.source_files = "ios/**/*.{h,m,mm}"
  s.requires_arc = true

  if fabric_enabled
    # folly_version must match the version used in React Native
    # See folly_version in react-native/React/FBReactNativeSpec/FBReactNativeSpec.podspec
    folly_version = '2021.06.28.00-v2'
    folly_compiler_flags = '-DFOLLY_NO_CONFIG -DFOLLY_MOBILE=1 -DFOLLY_USE_LIBCPP=1 -Wno-comma -Wno-shorten-64-to-32'

    s.pod_target_xcconfig = {
      'HEADER_SEARCH_PATHS' => '"$(PODS_ROOT)/boost" "$(PODS_ROOT)/boost-for-react-native" "$(PODS_ROOT)/RCT-Folly"'
    }
    s.platforms       = { ios: '11.0', tvos: '11.0' }
    s.compiler_flags  = folly_compiler_flags + ' -DRN_FABRIC_ENABLED'

    s.dependency "React"
    s.dependency "React-RCTFabric" # This is for fabric component
    s.dependency "React-Codegen"
    s.dependency "RCT-Folly", folly_version
    s.dependency "RCTRequired"
    s.dependency "RCTTypeSafety"
    s.dependency "ReactCommon/turbomodule/core"
  else
    s.platforms = { :ios => "9.0", :tvos => "9.0" }

    s.dependency "React-Core"
  end
end
