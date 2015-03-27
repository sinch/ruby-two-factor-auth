class Verification < ActiveRecord::Base
  validates_presence_of :phone_number, :code
end