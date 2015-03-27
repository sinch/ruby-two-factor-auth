class CreateVerifications < ActiveRecord::Migration
  def change
    create_table :verifications do |t|
      t.string :phone_number
      t.string :code
      t.timestamps
    end
  end
end
