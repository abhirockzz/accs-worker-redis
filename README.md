## Build

- `git clone https://github.com/abhirockzz/accs-worker-redis.git`
- `mvn clean install` - creates `accs-worker-redis.zip` in the `target` directory 

## Run locally

`java -jar accs-worker-redis.jar`

## Deploy to Oracle Application Container Cloud

- Install [PSM CLI](https://docs.oracle.com/en/cloud/paas/java-cloud/pscli/using-command-line-interface-1.html)
- Fill up Redis connectivity details in `deployment.json`
- Execute `psm accs push -n workedup -r java -s hourly -m manifest.json -d deployment.json -p target/accs-worker-redis.zip`

## For details

- check out the blog - [Worker applications on Oracle Application Container Cloud](https://medium.com/oracledevs/worker-applications-on-oracle-application-container-cloud-f449eaa77c39)