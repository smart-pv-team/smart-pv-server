import datetime
import datetime as dt

import matplotlib.dates as mdates
import matplotlib.pyplot
import matplotlib.pyplot as plt
import pymongo
from dateutil import tz

LOCAL_DB_URL = "mongodb://localhost:27018/smartpv"
PROD_DB_URL = "mongodb+srv://janicki:janicki@smartpv.dr74ruo.mongodb.net/smartpv?retryWrites=true&w=majority"


def run_drawer_prod(days: [datetime.datetime]):
  run_drawer(days, True, PROD_DB_URL)


def run_drawer_locally(days: [datetime.datetime]):
  run_drawer(days, False, LOCAL_DB_URL)


def run_drawer(days: [datetime.datetime], is_production_data: bool, db_url: str):
  for day in days:
    plt = prepare_plt(db_url, day, 0.001 if is_production_data else 1)
    file_name = str(day.date()) + "-" + ("production" if is_production_data else "local")
    file_path = "./charts/" + file_name
    save_plt(plt, file_path)
    show_plt(plt)


def show_plt(plt: matplotlib.pyplot.plot):
  plt.show()


def save_plt(plt: matplotlib.pyplot.plot, name: str):
  plt.gcf().savefig(name)


def prepare_plt(db_url: str, day: dt.datetime = dt.datetime, scale: float = 1):
  client = pymongo.MongoClient(db_url)
  db = client.smartpv
  measurement_entity_collection = db.measurementEntity
  consumption_entity_collection = db.consumptionEntity

  day_start = dt.datetime(day.year, day.month, day.day, day.hour, tzinfo=tz.UTC)
  day_end = day_start + dt.timedelta(days=1)

  measurements = list(map(lambda measurement: (datetime_to_datetime_without_seconds(measurement["date"]),
                                               measurement["measurement"]),
                          measurement_entity_collection
                          .find({"date": {'$lt': day_end, '$gt': day_start}})
                          .sort([("date", pymongo.ASCENDING)])))

  consumptions = list(map(lambda consumption: (datetime_to_datetime_without_seconds(consumption["date"]),
                                               consumption["activeDevicesNum"]),
                          consumption_entity_collection
                          .find({"date": {'$lt': day_end, '$gt': day_start}})
                          .sort([("date", pymongo.ASCENDING)])))

  measurements_x = list(map(lambda m: m[0] + dt.timedelta(hours=2), measurements))
  measurements_y = list(map(lambda m: m[1] * scale, measurements))
  consumptions_x = list(map(lambda m: m[0] + dt.timedelta(hours=2), consumptions))
  consumptions_y = list(map(lambda m: m[1], consumptions))
  plt.rcParams["figure.figsize"] = [24.00, 10]

  fig, ax1 = plt.subplots()
  ax2 = ax1.twinx()
  ax1.plot(measurements_x, measurements_y, 'y-')
  ax2.plot(consumptions_x, consumptions_y, 'g-')

  plt.gca().xaxis.set_major_formatter(mdates.DateFormatter('%H'))
  plt.gca().xaxis.set_major_locator(mdates.HourLocator(interval=1))
  plt.gcf().autofmt_xdate()

  day_start_warsaw = (day_start).astimezone(tz.gettz("Europe/Warsaw"))
  day_end_warsaw = (day_end).astimezone(tz.gettz("Europe/Warsaw"))
  ax1.set_xlabel('Odczyt z dni ' + str(day_start_warsaw) + " - " + str(day_end_warsaw), size=22)
  ax1.set_ylabel('Odczyt produkcji kW', color='y', size=22)
  ax2.set_ylabel('Ilość aktywnych urządzeń', color='g', size=22)
  ax2.set_ylim([0, 12])
  ax1.set_ylim([-10, 80])
  ax1.grid(True)
  plt.title('Wykres zależności odczytu produkcji i aktywnych urządzeń od czasu', size=22)
  return plt


def datetime_to_datetime_without_seconds(date: dt.datetime):
  return dt.datetime(date.year, date.month, date.day, date.hour, date.minute, 0, 0)


if __name__ == "__main__":
  days = [
    datetime.datetime(2022, 8, 28),
    datetime.datetime(2022, 8, 29),
    datetime.datetime(2022, 9, 18),
    datetime.datetime(2022, 9, 29),
    datetime.datetime(2022, 9, 30),
  ]
  run_drawer_locally(days)
  # run_drawer_prod(days)
