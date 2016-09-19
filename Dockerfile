FROM java:jdk

ENV DEBIAN_FRONTEND noninteractive

# Dependencies
RUN dpkg --add-architecture i386 && apt-get update && apt-get install -yq libstdc++6:i386 zlib1g:i386 libncurses5:i386 ant maven --no-install-recommends
ENV GRADLE_URL https://services.gradle.org/distributions/gradle-2.14.1-all.zip
RUN curl -L ${GRADLE_URL} -o /tmp/gradle-2.14.1-all.zip && unzip /tmp/gradle-2.14.1-all.zip -d /usr/local && rm /tmp/gradle-2.14.1-all.zip
ENV GRADLE_HOME /usr/local/gradle-2.14.1

# Download and untar SDK
ENV ANDROID_SDK_URL http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz
RUN curl -L ${ANDROID_SDK_URL} | tar xz -C /usr/local
ENV ANDROID_HOME /usr/local/android-sdk-linux

# Install Android SDK components
ENV ANDROID_SDK_COMPONENTS platform-tools,build-tools-21.1.2,build-tools-23.0.2
RUN echo y | ${ANDROID_HOME}/tools/android update sdk --no-ui --all --filter "${ANDROID_SDK_COMPONENTS}"
RUN echo y | ${ANDROID_HOME}/tools/android update sdk --no-ui --all --filter extra-android-m2repository
RUN echo y | ${ANDROID_HOME}/tools/android update sdk --no-ui --all --filter android-24

# Path
ENV PATH $PATH:${ANDROID_HOME}/tools:$ANDROID_HOME/platform-tools:${GRADLE_HOME}/bin

RUN mkdir $ANDROID_HOME/licenses
RUN echo -e "\n8933bad161af4178b1185d1a37fbf41ea5269c55" > "$ANDROID_HOME/licenses/android-sdk-license"

RUN mkdir epsdemo
RUN cd ./epsdemo
RUN git clone https://ilaszlo:ilaszlo1@github.com/columbooo/eps-demo.git
# RUN eps-demo/TestDemo/gradlew tasks

# RUN apt-get install apt-transport-https
# RUN apt-get update
# RUN echo "deb https://download.go.cd /" | tee /etc/apt/sources.list.d/gocd.list
# RUN curl https://download.go.cd/GOCD-GPG-KEY.asc | apt-key add -
# RUN apt-get update
# RUN apt-get install go-agent