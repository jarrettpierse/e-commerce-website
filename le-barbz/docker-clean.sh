#!/bin/bash
docker stop db db_admin || true
docker rm db db_admin || true
docker volume prune || true