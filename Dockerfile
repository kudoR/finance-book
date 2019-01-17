FROM maven
WORKDIR home/
RUN sh -c 'git clone https://github.com/kudoR/finance-book.git && cd finance-book && mvn clean install'
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /home/finance-book/target/app.jar" ]