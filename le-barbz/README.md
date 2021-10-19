# Le Barbz Web Development Project

## Video Walkthrough
https://www.youtube.com/watch?v=j4DiKg1tJEU

## Final Report PDF
https://drive.google.com/file/d/1gwP2J0vbUwCCylGMiezhPi2sv74rMVpB/view?usp=sharing

## Notice
If you reload the server, you **must** clear the client's cookies before accessing the site again. Failing to do so will introduce problems, as the HTTP Session can persist across server reloads. This will impede Spring Security's abiltiy to identify users, and will also interfere with our own session management in relation to customer carts.

## Getting Started

### Running on WSL

If you're using wsl (Windows Subsystem for Linux), you will need to forward ports 8081 and 3306. Port 8081 will be used to access PHPMyAdmin, a utility for observing and managing the database. Port 3306 is used to access the MySQL database.

### Setting up the Database

Stop and remove any containers named db or db_admin. This can be done using the docker-clean.sh script.

```bash
chmod u+x docker-clean.sh
./docker-clean.sh
```

Spin up docker containers for both MySQL and PhpMyAdmin

```bash
docker-compose up -d
```

Next, run the project via maven

```bash
mvn clean spring-boot:run
```

The website will be available at [localhost:8080](http://localhost:8080)

## Logging in as an Admin

To log in to the site as an admin, navigate to the [login page](http://localhost:8080) and log in using the following credentials.
| Username | Password |
| :--: | :--: |
| remy | chef |

## Accessing the Email Account

To access the email account used by this web app, navigate to [GMail](https://mail.google.com) and log in using the following credentials.

| EMail Address| Password|
| -- | -- |
| le.barbz.30860@gmail.com | supersecurepasswordforourecommerceplatform |

## Cleanup

If you would like to free all resources allocated by the project, use the docker-clean.sh script.

```bash
./docker-clean.sh
```

You might also want to remove all assets generated during the maven build process using the following command.

```bash
mvn clean
```

## Platform Support

This project has been tested on Debian 10 (Buster), Ubuntu 20.04 LTS (on WSL), MacOS 11 (Big Sur), and Manjaro Linux 5.11.2
